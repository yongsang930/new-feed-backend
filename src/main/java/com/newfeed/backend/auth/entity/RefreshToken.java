package com.newfeed.backend.auth.entity;

import com.newfeed.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    public RefreshToken(User user, String refreshToken, LocalDateTime expiredAt) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    /** 최근 사용 시각 업데이트 */
    public void updateLastUsed() {
        this.lastUsedAt = LocalDateTime.now();
    }

    /** 토큰 값 갱신 */
    public void updateToken(String refreshToken, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.lastUsedAt = LocalDateTime.now();
    }
}
