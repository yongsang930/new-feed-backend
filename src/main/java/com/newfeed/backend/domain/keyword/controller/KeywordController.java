package com.newfeed.backend.domain.keyword.controller;

import com.newfeed.backend.auth.principal.AuthPrincipal;
import com.newfeed.backend.domain.keyword.model.KeywordCreateRequest;
import com.newfeed.backend.domain.keyword.service.KeywordService;
import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("")
    public Api<?> list(@AuthenticationPrincipal AuthPrincipal principal) {
        Long userId = principal.getUserId();   // 이제 정상 작동
        return Api.OK(keywordService.getKeywordsWithSelection(userId)); // 정상 작동
    }

    @PostMapping("/custom")
    public Api<?> createCustomKeywords(@RequestBody KeywordCreateRequest request) {
        return Api.OK(keywordService.createCustomKeywords(request.getNames()));
    }

}
