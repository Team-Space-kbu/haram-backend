package com.space.haramspring.user.controller;

import com.space.haramspring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * getUser - Controller ㅣ
     * /v1/users 경로로 오는 접근이 security 인증을 받았다면,
     * body 에 있는 id를 통해 유저 정보를 조회한다.
     * @param userId 유저 아이디를 string 으로 받습니다.
     * @return Key Value 형태로 유저 정보를 반환한다.
     */
    @GetMapping
    public ResponseEntity<?> getUser(@RequestBody String userId) {

        log.debug("userId - {}", userId);
        Map<String, String> user = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

//    /**
//     * 넘어온 유저 객체를 데이터베이스에 Key: (User Id), Value: (User Object) 로 수정합니다.
//     *
//     * @param user 변경할 유저 객체를 받습니다.
//     * @return 변경된 유저 객체를 반환합니다.
//     */
//    @PutMapping
//    public ResponseEntity<?> updateUser(@RequestBody UserSignupDto user) {
//
//        Map<String, String> updateUser = userService.updateUser(user);
//
//        if (updateUser.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
//    }

//    /**
//     * 사용자 정보를 데이터 베이스에서 삭제합니다.
//     *
//     * @param userId 삭제할 유저 아이디를 받아옵니다.
//     * @return 성공 알림 문자열을 반환합니다. null 이면 실패.
//     */
//    @DeleteMapping
//    public ResponseEntity<?> deleteUser(@RequestBody String userId) {
//
//        Map<String, String> user = userService.deleteUser(userId);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }
}
