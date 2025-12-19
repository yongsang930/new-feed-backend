package com.newfeed.backend.domain.keyword.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newfeed.backend.domain.post.entity.PostKeyword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "keywords",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_keywords_en_name", columnNames = "en_name"),
                @UniqueConstraint(name = "uk_keywords_ko_en", columnNames = {"ko_name", "en_name"})
        }
)
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
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<PostKeyword> postKeywords = new ArrayList<>();
}

