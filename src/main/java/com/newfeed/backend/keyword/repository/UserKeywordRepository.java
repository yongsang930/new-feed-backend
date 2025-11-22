package com.newfeed.backend.keyword.repository;

import com.newfeed.backend.keyword.entity.UserKeyword;
import com.newfeed.backend.keyword.entity.UserKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {

    List<UserKeyword> findByUser_UserId(Long userId);

    boolean existsByUser_UserIdAndKeyword_KeywordId(Long userId, Long keywordId);
}
