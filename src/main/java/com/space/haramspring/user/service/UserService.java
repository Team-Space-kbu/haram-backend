package com.space.haramspring.user.service;

import com.space.haramspring.user.dto.UserSignupDto;
import com.space.haramspring.user.dto.UserLoginDto;

import java.util.Map;

public interface UserService {

    /**
     * getUserInfo - Service
     * user 정보를 조회합니다. 객체 전부를 받지 않고, 유저가 직접 확인할 수 있는 정보만 제공한다.
     * Key (userSeqNum) : Value (User) 로 저장되어 있는 store Map 을 이용한다.
     * @param userId 사용자의 로그인 아이디를 통해 사용자 정보를 조회한다.
     * @return 사용자 정보를 반환한다.
     */
    Map<String, String> getUserInfo(String userId);

    /**
     * addUser - Service ㅣ
     * 사용자의 정보를 메모리 데이터 베이스에 저장한다.
     *
     * @param user 사용자 객체를 받는다.
     */
    Map<String, String> addUser(UserSignupDto user);

//    /**
//     * 사용자의 정보를 데이터 베이스에서 수정합니다.
//     * @param user 변경되는 사용자 객체를 받습니다.
//     * @return 변경된 객체를 반환합니다.
//     */
//    Map<String, String> updateUser(UserSignupDto user);

//    /**
//     * 사용자의 정보를 데이터 베이스에서 삭제합니다.
//     * @param userId 삭제할 유저 정보의 아이디를 받습니다.
//     * @return 삭제 결과 값을 true or false 로 반환합니다.
//     */
//    Map<String, String> deleteUser(String userId);

    /**
     * loginUser - Service ㅣ
     * @param userLoginDto 받아온 로그인 데이터를 통해 인증을 실행하고,
     * @return accessToken 값을 map 으로 제공한다.
     */
    Map<String, String> loginUser(UserLoginDto userLoginDto);

}
