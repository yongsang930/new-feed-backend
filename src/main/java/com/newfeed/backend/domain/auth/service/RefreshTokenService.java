package com.newfeed.backend.domain.auth.service;

import com.newfeed.backend.domain.auth.entity.RefreshToken;
import com.newfeed.backend.domain.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /** 리프레시 토큰 저장 (게스트 userId 음수 가능) */
    public void save(Long userId, String refreshToken) {
        RefreshToken tokenEntity = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiredAt(LocalDateTime.now().plusDays(30))  // 예시: 30일
                .build();
        refreshTokenRepository.save(tokenEntity);
    }

    /** 리프레시 토큰 조회 */
    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElse(null);
    }

    /** userId 기준 토큰 삭제 */
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
