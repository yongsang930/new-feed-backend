package com.newfeed.backend.post.repository;

import com.newfeed.backend.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByLinkHash(String linkHash);
}
