package com.newfeed.backend.keyword.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserKeywordId implements Serializable {

    private Long userId;
    private Long keywordId;

    public UserKeywordId() {}

    public UserKeywordId(Long userId, Long keywordId) {
        this.userId = userId;
        this.keywordId = keywordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserKeywordId)) return false;
        UserKeywordId that = (UserKeywordId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(keywordId, that.keywordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, keywordId);
    }
}
