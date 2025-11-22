package com.newfeed.backend.keyword.entity;

import com.newfeed.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_preferred_keywords")
@Getter
@NoArgsConstructor
public class UserKeyword {

    @EmbeddedId
    private UserKeywordId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("keywordId")
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    public UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
        this.id = new UserKeywordId(user.getUserId(), keyword.getKeywordId());
    }
}
