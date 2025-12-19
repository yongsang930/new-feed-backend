package com.newfeed.backend.domain.keyword.service;

import com.newfeed.backend.domain.keyword.entity.Keyword;
import com.newfeed.backend.domain.keyword.entity.UserPreferredKeyword;
import com.newfeed.backend.domain.keyword.model.KeywordResponse;
import com.newfeed.backend.domain.keyword.repository.KeywordRepository;
import com.newfeed.backend.domain.keyword.repository.UserPreferredKeywordRepository;
import com.newfeed.backend.global.error.CommonErrorCode;
import com.newfeed.backend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserPreferredKeywordRepository userPreferredKeywordRepository;

    public List<KeywordResponse> getKeywordsWithSelection(Long userId) {

        List<Keyword> all = keywordRepository.findByIsActiveTrue();

        // 게스트
        if (userId < 0) {
            return all.stream()
                    .map(k -> new KeywordResponse(k.getKeywordId(), k.getEnName(), k.getKoName(), k.getIsActive(), false))
                    .toList();
        }

        // 회원 키워드 조회
        Set<Long> selected = userPreferredKeywordRepository.findByIdUserId(userId)
                .stream()
                .map(UserPreferredKeyword::getKeywordId)
                .collect(Collectors.toSet());

        // 매핑
        return all.stream()
                .map(k -> new KeywordResponse(
                        k.getKeywordId(),
                        k.getEnName(),
                        k.getKoName(),
                        k.getIsActive(),
                        selected.contains(k.getKeywordId())
                ))
                .toList();
    }

    public List<KeywordResponse> createCustomKeywords(List<String> names) {

        List<String> cleaned = names.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();

        // 이미 DB에 존재하는 키워드는 제외
        List<Keyword> existing = keywordRepository.findByKoNameInOrEnNameIn(cleaned, cleaned);

        Set<String> existingNames = existing.stream()
                .flatMap(k -> Stream.of(k.getKoName(), k.getEnName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Keyword> newKeywords = cleaned.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> Keyword.builder()
                        .koName(name)     // koName, enName 어떤 걸 기준으로 둘지 결정 필요
                        .enName(name)
                        .isActive(false)  // 사용자 입력 키워드는 기본 false
                        .build()
                )
                .toList();

        keywordRepository.saveAll(newKeywords);

        // DTO 변환
        return newKeywords.stream()
                .map(KeywordResponse::from)
                .toList();
    }

    private List<String> cleanInput(List<String> names) {
        return names.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();
    }

    private void validateInput(List<String> names) {
        if (names.isEmpty()) {
            throw new ApiException(CommonErrorCode.INVALID_KEYWORD_INPUT);
        }

        for (String name : names) {
            if (name.length() > 50) {
                throw new ApiException(CommonErrorCode.KEYWORD_TOO_LONG);
            }
            if (!name.matches("^[a-zA-Z0-9가-힣 ]+$")) {
                throw new ApiException(CommonErrorCode.KEYWORD_INVALID_CHAR);
            }
        }
    }

}
