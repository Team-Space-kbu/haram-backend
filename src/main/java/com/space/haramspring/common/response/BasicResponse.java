package com.space.haramspring.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class BasicResponse {
    private HttpStatus status;


    @Builder
    public ResponseEntity Response(String location, Object responseBody){
        return ResponseEntity
                .status(status)
                .header("Location", location)
                .body(responseBody);
    }
}
