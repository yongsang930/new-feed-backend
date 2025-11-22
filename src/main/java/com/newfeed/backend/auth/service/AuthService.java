package com.newfeed.backend.auth.service;

import com.newfeed.backend.auth.dto.LoginRequest;
import com.newfeed.backend.auth.dto.TokenResponse;
import com.newfeed.backend.auth.entity.RefreshToken;
import com.newfeed.backend.auth.repository.RefreshTokenRepository;
import com.newfeed.backend.global.exception.CustomException;
import com.newfeed.backend.global.exception.ExceptionCode;
import com.newfeed.backend.global.jwt.JwtTokenProvider;
import com.newfeed.backend.user.entity.User;
import com.newfeed.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 → AccessToken + RefreshToken 발급
     */
    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        return issueTokens(user);
    }

    /**
     * AccessToken + RefreshToken 묶어서 발급하는 핵심 로직
     */
    private TokenResponse issueTokens(User user) {

        // Access Token
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getEmail()
        );

        // Refresh Token
        String refreshTokenStr = jwtTokenProvider.createRefreshToken(user.getUserId());
        LocalDateTime expiry = jwtTokenProvider.getRefreshTokenExpiry();

        // DB 존재 여부 확인 후 갱신 or 새로 생성
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken(user, refreshTokenStr, expiry));

        refreshToken.updateToken(refreshTokenStr, expiry);
        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshTokenStr);
    }

    /**
     * Refresh Token 기반 AccessToken 재발급
     */
    public TokenResponse refresh(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ExceptionCode.INVALID_JWT));

        // 만료 확인
        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ExceptionCode.EXPIRED_JWT);
        }

        // 마지막 사용 시간 업데이트
        token.updateLastUsed();
        refreshTokenRepository.save(token);

        User user = token.getUser();

        String newAccessToken = jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getEmail()
        );

        return new TokenResponse(newAccessToken, refreshToken);
    }

    /**
     * 로그아웃 → Refresh Token 삭제
     */
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        refreshTokenRepository.deleteByUser(user);
    }
}
