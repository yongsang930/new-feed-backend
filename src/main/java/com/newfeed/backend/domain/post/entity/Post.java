package com.newfeed.backend.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "posts",
        indexes = {
                @Index(name = "idx_posts_published_at", columnList = "published_at DESC")
        }
)
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 2048)
    private String link;

    @Column(name = "link_hash", nullable = false, length = 64)
    private String linkHash;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(nullable = false, length = 20)
    private String region;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
