package com.space.haramspring.user.repository;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.user.dto.UserSignupDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    // 회원 저장 (Create)
    User saveUser(UserSignupDto user);

    // 회원 ID로 회원 조회 (Read)
    Long getSeqNumFindById(String loginId) ;

    // userSeqNum 값으로 회원 조회 (Read)
    Optional<User> findByUserSeq(Long userSeqNum);

    // 모든 회원 조회 (Read)
    List<User> findAllUser();

//    // 회원 정보 변경 (Update)
//    User updateUser(Long userSeqNum, UserSignupDto updatedUser);

    // userSeqNum 값으로 회원 삭제 (Delete)
    // 조건 ( user 당사자만 삭제 가능 - 회원탈퇴. )
    void removeUser(Long userSeqNum, String userId) ;

}
