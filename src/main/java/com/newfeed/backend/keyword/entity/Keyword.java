package com.newfeed.backend.keyword.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "keywords",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_keywords_en_name", columnNames = "en_name"),
                @UniqueConstraint(name = "uk_keywords_ko_en", columnNames = {"ko_name", "en_name"})
        }
)
@Getter
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long keywordId;

    @Column(name = "en_name", nullable = false, length = 50)
    private String enName;

    @Column(name = "ko_name", nullable = false, length = 50)
    private String koName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
