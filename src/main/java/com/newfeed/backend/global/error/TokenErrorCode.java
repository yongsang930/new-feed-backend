package com.newfeed.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{

    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰"),
    REFRESH_TOKEN_EXPIRED(401, 2001, "리프레시 토큰이 만료되었습니다."),
    ACCESS_TOKEN_EXPIRED(401, 2100, "엑세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, 2005, "리프레시 토큰을 찾을 수 없습니다."),
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "인증 토큰 없음"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
