package com.newfeed.backend.domain.keyword.controller;

import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {

    @GetMapping("")
    public Api<?> getKeywords() {
        // 임시 테스트 데이터
        return Api.OK(
                List.of("AI", "Cloud", "Security", "Backend", "Frontend")
        );
    }
}
