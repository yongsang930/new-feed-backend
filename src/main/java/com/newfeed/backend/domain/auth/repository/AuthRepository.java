package com.newfeed.backend.domain.auth.repository;

import com.newfeed.backend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByPublishedAtDesc(Pageable pageable);
}
