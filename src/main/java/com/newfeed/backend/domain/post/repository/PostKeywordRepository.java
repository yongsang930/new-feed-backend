package com.newfeed.backend.domain.post.repository;

import com.newfeed.backend.domain.post.entity.PostKeyword;
import com.newfeed.backend.domain.post.entity.PostKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostKeywordRepository extends JpaRepository<PostKeyword, PostKeywordId> {

    List<PostKeyword> findByPost_PostId(Long postId);

    List<PostKeyword> findByKeyword_KeywordId(Long keywordId);
}
