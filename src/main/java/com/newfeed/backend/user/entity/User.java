package com.newfeed.backend.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_type", nullable = false, length = 20)
    private String loginType;

    @Column(name = "social_id", unique = true, length = 255)
    private String socialId;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "nickname", length = 50)
    private String nickName;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;


    // ⭐ builder가 사용할 생성자
    @Builder
    public User(String loginType, String socialId,
                String email, String nickName) {
        this.loginType = loginType;
        this.socialId = socialId;
        this.email = email;
        this.nickName = nickName;
    }
}
