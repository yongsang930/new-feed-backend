package com.newfeed.backend.user.repository;

import com.newfeed.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 로그인 시에도 사용할 수 있음
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
