package com.newfeed.backend.auth.repository;

import com.newfeed.backend.auth.entity.RefreshToken;
import com.newfeed.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUser(User user);
}
