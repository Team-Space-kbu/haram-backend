package com.space.haramspring.user.service;

import com.space.haramspring.core.entity.users.User;

public interface UserService {

    /**
     * 사용자의 정보를 메모리 데이터 베이스에서 불러옵니다.
     * @param userId 사용자 아이디 정보를 받습니다.
     * @return 사용자 정보 객체를 반환합니다.
     */
    User getUserInfoByRepository(String userId);

    /**
     * 사용자의 정보를 메모리 데이터 페이스에 저장합니다.
     * @param user 사용자 객체를 받습니다.
     * @return 사용자 정보 객체를 반환합니다.
     */
    User addUserByRepository(User user);

    /**
     * 사용자의 정보를 데이터 베이스에서 수정합니다.
     * @param user 변경되는 사용자 객체를 받습니다.
     * @return 변경된 객체를 반환합니다.
     */
    User updateUserByRepository(User user);

    /**
     * 사용자의 정보를 데이터 베이스에서 삭제합니다.
     * @param userId 삭제할 유저 정보의 아이디를 받습니다.
     * @return 삭제 결과 값을 true or false 로 반환합니다.
     */
    Boolean deleteUserByRepository(String userId);
}
