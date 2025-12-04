package com.newfeed.backend.domain.keyword.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_preferred_keywords")
public class UserPreferredKeyword {

    @EmbeddedId
    private UserPreferredKeywordId id;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class UserPreferredKeywordId {

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "keyword_id")
        private Long keywordId;
    }

    public Long getUserId() {
        return id.getUserId();
    }

    public Long getKeywordId() {
        return id.getKeywordId();
    }
}
