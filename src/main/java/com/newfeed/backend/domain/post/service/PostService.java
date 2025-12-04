package com.newfeed.backend.domain.post.service;

import com.newfeed.backend.domain.post.entity.Post;
import com.newfeed.backend.domain.post.model.PostResponse;
import com.newfeed.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<PostResponse> getLatestPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<Post> postsPage = postRepository.findAll(pageable);
        return postsPage.map(PostResponse::from);
    }

    public Page<PostResponse> getPostsByKeywordIds(List<Long> keywordIds, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());

        // null 제거
        List<Long> cleaned = keywordIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (cleaned.isEmpty()) {
            return getLatestPosts(page, size);
        }

        Page<Post> postsPage = postRepository.findDistinctByPostKeywordsKeywordKeywordIdIn(cleaned, pageable);

        return postsPage.map(PostResponse::from);
    }
}
