package com.newfeed.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCodeIfs {

    OK(200, 0, "성공"),
    
    // 일반 요청 오류
    BAD_REQUEST(400, 4000, "잘못된 요청입니다."),
    VALIDATION_ERROR(400, 4001, "요청 데이터 검증에 실패했습니다."),
    METHOD_NOT_ALLOWED(405, 4002, "허용되지 않은 메서드입니다."),

    // 인증/권한
    UNAUTHORIZED(401, 4100, "인증이 필요합니다."),
    FORBIDDEN(403, 4101, "접근 권한이 없습니다."),

    // 리소스 오류
    NOT_FOUND(404, 4200, "요청한 리소스를 찾을 수 없습니다."),

    // Keyword
    INVALID_KEYWORD_INPUT(400, 4300, "키워드 입력이 비어있습니다."),
    KEYWORD_TOO_LONG(400, 4301, "키워드는 50자를 초과할 수 없습니다."),
    KEYWORD_INVALID_CHAR(400, 4302, "키워드는 한글, 영문, 숫자, 공백만 허용됩니다."),

    // 500번대
    SERVER_ERROR(500, 5000, "서버 내부 오류가 발생했습니다.");
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
