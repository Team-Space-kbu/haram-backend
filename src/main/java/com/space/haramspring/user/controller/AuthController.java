package com.space.haramspring.user.controller;

import com.space.haramspring.user.dto.UserSignupDto;
import com.space.haramspring.user.dto.UserLoginDto;
import com.space.haramspring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class AuthController {

    private final UserService userService;

    /**
     * signUp - Controller ㅣ
     * 회원가입 요청이다. 유저의 정보가 제대로 들어왔다면, addUser 메서드를 사용하여 회원가입 처리를 한다.
     * @param userSignupDto 회원가입에 필요한 정보를 객체로 받아온다.
     * @return 만들어진 유저 객체를 Key Value 형태로 반환한다.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignupDto userSignupDto) {

        log.debug("[AuthController] - /v1/signup");
        Map<String, String> user = userService.addUser(userSignupDto);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * login - Controller ㅣ
     * 회원 정보가 있는 유저의 요청이 들어오면 로그인을 할 수 있다.
     * @param userLoginDto 로그인에 필요한 정보를 객체로 받아온다.
     * @return 로그인에 성공했다면, accessToken 을 반환한다.
     */
    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {

        log.debug("[AuthController] - /v1/login");

        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(userLoginDto));
    }

    /**
     * test - Controller ㅣ
     * User 접근 권한을 테스트 하기 위한 컨트롤러이다.
     * @return "OK"
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {

        log.debug("[AuthController] - /v1/test");

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(){

        log.debug("[AuthController] - /v1/logout");
        return ResponseEntity.ok(200);
    }
}
