package com.newfeed.backend.domain.post.service;

import com.newfeed.backend.domain.post.entity.Post;
import com.newfeed.backend.domain.post.model.PostResponse;
import com.newfeed.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 최신순 전체 게시물 조회
    public List<PostResponse> getLatestPosts() {
        return postRepository.findAllByOrderByPublishedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    // 상세 조회
    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return PostResponse.from(post);
    }
}
