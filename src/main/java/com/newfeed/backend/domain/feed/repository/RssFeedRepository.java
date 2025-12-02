package com.newfeed.backend.domain.feed.repository;

import com.newfeed.backend.domain.feed.entity.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {

    List<RssFeed> findByIsActiveTrue();

    List<RssFeed> findByRegion(String region);
}
