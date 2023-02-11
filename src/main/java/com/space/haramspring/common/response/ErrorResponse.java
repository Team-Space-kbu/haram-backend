package com.space.haramspring.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public ErrorResponse(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = code;
        this.errorMessage = message;
    }
}