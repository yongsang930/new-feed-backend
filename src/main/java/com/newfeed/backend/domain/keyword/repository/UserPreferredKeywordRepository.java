package com.newfeed.backend.domain.keyword.repository;

import com.newfeed.backend.domain.keyword.entity.UserPreferredKeyword;
import com.newfeed.backend.domain.keyword.entity.UserPreferredKeyword.UserPreferredKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPreferredKeywordRepository extends JpaRepository<UserPreferredKeyword, UserPreferredKeywordId> {

    List<UserPreferredKeyword> findByIdUserId(Long userId);
}
