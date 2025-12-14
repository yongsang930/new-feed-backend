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

    // 게스트/유저 만료일 정책
    private static final int GUEST_EXPIRE_DAYS = 3;   // 게스트 3일
    private static final int USER_EXPIRE_DAYS  = 30;  // 정식 로그인 30일

    /**
     * Refresh Token 저장
     * - 게스트: userId < 0
     * - 유저: userId > 0
     */
    public void save(Long userId, String refreshToken) {

        LocalDateTime expiredAt;

        if (userId < 0) {
            // 게스트
            expiredAt = LocalDateTime.now().plusDays(GUEST_EXPIRE_DAYS);
        } else {
            // 정식 유저
            expiredAt = LocalDateTime.now().plusDays(USER_EXPIRE_DAYS);
        }

        RefreshToken tokenEntity = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiredAt(expiredAt)
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

    public void update(RefreshToken token) {
        refreshTokenRepository.save(token);
    }
}
