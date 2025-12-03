package com.newfeed.backend.domain.post.controller;

import com.newfeed.backend.global.api.Api;
import com.newfeed.backend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 최신 게시물 목록 조회
    @GetMapping("")
    public Api<?> list() {
        return Api.OK(postService.getLatestPosts());
    }

    // 게시물 상세 조회
    @GetMapping("/{postId}")
    public Api<?> detail(@PathVariable Long postId) {
        return Api.OK(postService.getPostDetail(postId));
    }
}
