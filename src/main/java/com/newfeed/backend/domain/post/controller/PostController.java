package com.newfeed.backend.domain.post.controller;

import com.newfeed.backend.domain.post.service.PostService;
import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 전체 게시물 목록 조회
    @GetMapping("")
    public Api<?> list(  @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "30") int size) {
        return Api.OK(postService.getLatestPosts(page, size));
    }

    @GetMapping("/search")
    public Api<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) List<Long> keywordIds
    ) {
        if (keywordIds == null || keywordIds.isEmpty()) {
            return Api.OK(postService.getLatestPosts(page, size));
        }
        return Api.OK(postService.getPostsByKeywordIds(keywordIds, page, size));
    }
}
