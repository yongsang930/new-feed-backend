package com.newfeed.backend.domain.post.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * PostKeywordId
 *
 * - post_keywords 테이블의 복합키(Composite Primary Key)를 표현하는 클래스
 * - JPA에서는 복합키 사용 시 반드시 @Embeddable 또는 @IdClass 중 하나를 사용해야 하며,
 *   EmbeddedId 방식에서는 이처럼 별도 PK 클래스를 정의함.
 */
@Embeddable
public class PostKeywordId implements Serializable {

    /**
     * 게시글 ID (posts.post_id)
     * - 복합 PK 구성 요소 1
     * - 실제 Post 엔티티의 PK 값을 매핑함
     */
    private Long postId;

    /**
     * 키워드 ID (keywords.keyword_id)
     * - 복합 PK 구성 요소 2
     * - 실제 Keyword 엔티티의 PK 값을 매핑함
     */
    private Long keywordId;

    /**
     * JPA 표준 스펙에서 요구하는 기본 생성자
     * - 프레임워크 사용을 위해 반드시 필요 (public or protected)
     */
    public PostKeywordId() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * - PostKeyword 엔티티를 생성할 때 자동으로 PK를 구성하는 용도로 사용됨
     */
    public PostKeywordId(Long postId, Long keywordId) {
        this.postId = postId;
        this.keywordId = keywordId;
    }

    /**
     * equals()
     * - JPA에서 복합키를 정확히 비교하기 위해 반드시 override 해야 함
     * - PK 비교시 두 필드(postId, keywordId)가 모두 동일해야 같은 엔티티로 취급됨
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostKeywordId)) return false;
        PostKeywordId that = (PostKeywordId) o;
        return Objects.equals(postId, that.postId) &&
                Objects.equals(keywordId, that.keywordId);
    }

    /**
     * hashCode()
     * - JPA 컬렉션 관리 및 캐시 비교를 위해 반드시 재정의 필요
     * - equals와 함께 복합키 비교가 일관되도록 유지함
     */
    @Override
    public int hashCode() {
        return Objects.hash(postId, keywordId);
    }
}
