package com.newfeed.backend.domain.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;     // ENUM 이름
    private String message;  // 메시지
    private int status;      // HTTP 상태
}
