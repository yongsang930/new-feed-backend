package com.newfeed.backend.domain.keyword.model;

import com.newfeed.backend.domain.keyword.entity.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordResponse {
    private Long keywordId;
    private final String enName;
    private final String koName;
    private final boolean isActive;
    private boolean selected;

    public static KeywordResponse from(Keyword keyword) {
        return new KeywordResponse(
                keyword.getKeywordId(),
                keyword.getEnName(),
                keyword.getKoName(),
                keyword.getIsActive(),
                false                  // Post 상세 조회에서는 기본적으로 selected = false
        );
    }
}
