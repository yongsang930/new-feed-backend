package com.newfeed.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 404),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다.", 400),
    EMAIL_DUPLICATION("이미 존재하는 이메일입니다.", 400),

    INVALID_JWT("유효하지 않은 JWT 토큰입니다.", 401),
    EXPIRED_JWT("만료된 JWT 토큰입니다.", 401),

    AUTH_REQUIRED("인증이 필요합니다.", 401),
    ACCESS_DENIED("접근 권한이 없습니다.", 403),

    INVALID_USER("존재하지 않는 사용자입니다.", 400),

    // 🔥 디폴트 에러 추가
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다.", 500);

    private final String message;
    private final int status;
}
