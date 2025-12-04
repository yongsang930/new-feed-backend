package com.newfeed.backend.domain.post.model;

import com.newfeed.backend.domain.keyword.model.KeywordResponse;
import com.newfeed.backend.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String link;
    private final String summary;
    private final String content;
    private final String source;
    private final String region;
    private final LocalDateTime publishedAt;
    private final List<KeywordResponse> keywords;

    @Builder
    public PostResponse(
            Long id,
            String title,
            String link,
            String summary,
            String content,
            String source,
            String region,
            LocalDateTime publishedAt,
            List<KeywordResponse> keywords
    ) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.summary = summary;
        this.content = content;
        this.source = source;
        this.region = region;
        this.publishedAt = publishedAt;
        this.keywords = keywords;
    }

    public static PostResponse from(Post post) {

        List<KeywordResponse> keywords =
                post.getPostKeywords().stream()
                        .map(pk -> KeywordResponse.from(pk.getKeyword()))
                        .toList();

        return PostResponse.builder()
                .id(post.getPostId())
                .title(post.getTitle())
                .link(post.getLink())
                .summary(post.getSummary())
                .content(post.getContent())
                .source(post.getSource())
                .region(post.getRegion())
                .publishedAt(post.getPublishedAt())
                .keywords(keywords)
                .build();
    }
}
