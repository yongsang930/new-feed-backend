package com.newfeed.backend.domain.post.model;

import com.newfeed.backend.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String link;
    private final String summary;
    private final String region;
    private final LocalDateTime publishedAt;

    @Builder
    public PostResponse(
            Long id,
            String title,
            String link,
            String summary,
            String region,
            LocalDateTime publishedAt
    ) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.summary = summary;
        this.region = region;
        this.publishedAt = publishedAt;
    }

    // Entity → DTO 변환
    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getPostId())
                .title(post.getTitle())
                .link(post.getLink())
                .summary(post.getSummary())
                .region(post.getRegion())
                .publishedAt(post.getPublishedAt())
                .build();
    }
}
