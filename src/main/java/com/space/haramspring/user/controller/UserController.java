package com.space.haramspring.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/v1/users")
    public ResponseEntity<String> getUser(){

        return ResponseEntity.status(HttpStatus.OK).body("TEST");
    }

    @PostMapping("/v1/users")
    public ResponseEntity<String> addUser(){

        return ResponseEntity.status(HttpStatus.OK).body("TEST");
    }

    @DeleteMapping("/v1/users")
    public ResponseEntity<String> deleteUser(){

        return ResponseEntity.status(HttpStatus.OK).body("TEST");
    }

    @PatchMapping("/v1/users")
    public ResponseEntity<String> updateUser(){

        return ResponseEntity.status(HttpStatus.OK).body("TEST");
    }
}
