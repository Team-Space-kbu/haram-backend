package com.space.haramspring.user.controller;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 관리자가 parameter 정보로 유저 정보를 조회합니다.
     * @param userId 유저 아이디를 string 으로 받습니다.
     * @return 유저 객체를 반환합니다.
     */
    @GetMapping
    public ResponseEntity<User> getUser(@RequestBody String userId){

        log.info("[UserController(/v1/users) - GET] 관리자가 {}의 유저 정보 요청", userId);
        User user = userService.getUserInfoByRepository(userId);

        if(user == null) {

            log.info("[UserController(/v1/users) - GET] {} 해당 아이디로 된 정보 없음", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        log.info("[UserController(/v1/users) - GET] 관리자에게 {}의 유저 정보 반환", user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * 넘어온 유저 객체를 데이터베이스에 Key: (User Id), Value: (User Object) 로 저장합니다.
     * @param user 유저 객체를 받습니다.
     * @return 유저 객체를 반환합니다.
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){

        String userId = user.getId();

        log.info("[UserController(/v1/users) - POST] {} 해당 유저 객체 저장 요청", userId);
        User isUser = userService.getUserInfoByRepository(userId);

        if (isUser != null) {
            log.info("[UserController(/v1/users) - POST] {}는 이미 존재하는 아이디 입니다.", userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        User saveUser = userService.addUserByRepository(user);
        log.info("[UserController(/v1/users) - POST] {} 저장된 유저 객체 정보", saveUser);
        return ResponseEntity.status(HttpStatus.OK).body(saveUser);
    }

    /**
     * 넘어온 유저 객체를 데이터베이스에 Key: (User Id), Value: (User Object) 로 수정합니다.
     * @param user 변경할 유저 객체를 받습니다.
     * @return 변경된 유저 객체를 반환합니다.
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){

        String userId = user.getId();
        log.info("[UserController(/v1/users) - PUT] {} 해당 아이디 정보 수정 요청", userId);

        User isUser = userService.getUserInfoByRepository(userId);

        if (isUser == null) {
            log.info("[UserController(/v1/users) - PUT] {} 해당 아이디로 된 정보 없음", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User newUser = userService.updateUserByRepository(user);
        log.info("[UserController(/v1/users) - PUT] {} 수정된 유저 객체", user);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    /**
     * 사용자 정보를 데이터 베이스에서 삭제합니다.
     * @param userId 삭제할 유저 아이디를 받아옵니다.
     * @return 성공 알림 문자열을 반환합니다. null 이면 실패.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody String userId){

        log.info("[UserController(/v1/users) - DELETE] {} 해당 아이디 정보 수정 요청", userId);
        Boolean isSuccess = userService.deleteUserByRepository(userId);

        if(!isSuccess) {
            log.info("[UserController(/v1/users) - DELETE] {} 해당 아이디로 된 정보 없음", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        log.info("[UserController(/v1/users) - DELETE] {} 정보 삭제 완료.", userId);
        return ResponseEntity.status(HttpStatus.OK).body("complete");
    }
}
