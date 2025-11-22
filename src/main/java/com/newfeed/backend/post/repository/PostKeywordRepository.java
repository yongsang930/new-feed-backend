package com.newfeed.backend.post.repository;

import com.newfeed.backend.post.entity.PostKeyword;
import com.newfeed.backend.post.entity.PostKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostKeywordRepository extends JpaRepository<PostKeyword, PostKeywordId> {

    List<PostKeyword> findByPost_PostId(Long postId);

    List<PostKeyword> findByKeyword_KeywordId(Long keywordId);
}
