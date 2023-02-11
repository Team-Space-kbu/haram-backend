package com.space.haramspring.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/v1/login")
    public ResponseEntity Login(){

        return ResponseEntity.ok(200);
    }

    @PostMapping("/v1/logout")
    public ResponseEntity Logout(){

        return ResponseEntity.ok(200);
    }
}
