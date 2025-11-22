package com.newfeed.backend.post.entity;

import com.newfeed.backend.keyword.entity.Keyword;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PostKeyword
 *
 * - 게시글(Post)과 키워드(Keyword)의 N:N 관계를 표현하는 조인 테이블 엔티티
 * - 실제 테이블 이름: post_keywords
 * - 복합키(PostKeywordId)를 사용해 (post_id, keyword_id) 쌍을 PK로 구성함
 */
@Entity
@Table(name = "post_keywords")
@Getter
@NoArgsConstructor
public class PostKeyword {

    /**
     * 복합키(Composite Primary Key)
     *
     * - post_id + keyword_id를 합쳐 하나의 PK 역할을 수행함
     * - @EmbeddedId 사용 시 PostKeywordId 클래스가
     *   복합키를 표현하는 Embeddable 클래스가 된다.
     */
    @EmbeddedId
    private PostKeywordId id;

    /**
     * Post 엔티티 연관관계
     *
     * - 다대일(ManyToOne): 여러 PostKeyword가 하나의 Post를 참조
     * - @MapsId("postId"): 복합키(PostKeywordId) 내 postId 필드를
     *   이 연관관계의 FK 값으로 사용한다는 의미
     * - 즉, PostKeywordId.postId = post.getPostId()
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * Keyword 엔티티 연관관계
     *
     * - 다대일(ManyToOne): 여러 PostKeyword가 하나의 Keyword를 참조
     * - @MapsId("keywordId"): 복합키 내 keywordId 값을 연관된 Keyword의 PK로 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("keywordId")
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    /**
     * 편의 생성자
     *
     * - Post와 Keyword를 받아서 PostKeywordId 복합키를 자동 생성
     * - 양 연관관계를 초기화하면서 id 값까지 일관되게 세팅하는 역할
     * - 이 생성자를 사용하면 new PostKeyword(post, keyword) 만으로
     *   모든 값이 정상적으로 설정됨
     */
    public PostKeyword(Post post, Keyword keyword) {
        this.post = post;
        this.keyword = keyword;

        // 복합키 자동 구성 (post_id + keyword_id)
        this.id = new PostKeywordId(post.getPostId(), keyword.getKeywordId());
    }
}
