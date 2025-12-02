package com.newfeed.backend.domain.feed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * RSS 피드 정보 엔티티
 *
 * - Python RSS 배치가 이 테이블을 조회하여 수집 작업을 수행함
 * - region: DOMESTIC / GLOBAL
 * - is_active: 수집 여부 ON/OFF
 * - last_crawled_at: 마지막 수집 시간
 */
@Entity
@Table(name = "rss_feeds")
@Getter
@NoArgsConstructor
public class RssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long feedId;

    /**
     * 지역(DOMESTIC, GLOBAL)
     */
    @Column(name = "region", nullable = false, length = 20)
    private String region;

    /**
     * RSS URL
     */
    @Column(name = "feed_url", nullable = false, length = 2048, unique = true)
    private String feedUrl;

    /**
     * 활성 여부 (true면 배치에서 수집함)
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /**
     * 마지막 수집 시간 (Python 배치에서 업데이트)
     */
    @Column(name = "last_crawled_at")
    private LocalDateTime lastCrawledAt;

    /**
     * 생성 시각 (DB default now())
     */
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 새로운 RSS Feed 등록 시 사용하는 생성자
     */
    public RssFeed(String region, String feedUrl) {
        this.region = region;
        this.feedUrl = feedUrl;
        this.isActive = true;
    }

    /**
     * 배치에서 수집 시간 업데이트
     */
    public void updateLastCrawledAt(LocalDateTime time) {
        this.lastCrawledAt = time;
    }

    /**
     * 피드 활성/비활성
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }
}
