package com.space.haramspring.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;


@Getter
public class SuccessResponse {
    private final HttpStatus httpStatus;

    private final Map<String, Object> responseBody;

    @Builder
    public SuccessResponse(HttpStatus httpStatus, Map<String, Object> responseBody) {
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }
}


