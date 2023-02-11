package com.space.haramspring.common.code;

import com.space.haramspring.common.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    LOGIN_FAIL( "LG01", "Authentication failed (로그인을 실패하였습니다.)"),
    LOGIN_TO_MUCH( "LG02", "Authentication failed (너무 많은 로그인 요청을 했습니다.)");

    private final String code;
    private final String message;

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

}
