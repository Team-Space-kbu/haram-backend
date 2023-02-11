package com.space.haramspring.user.repository;

import com.space.haramspring.core.entity.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /**
     * 사용자를 정보를 데이터 베이스에 저장합니다.
     * @param users 유저 저장 정보를 받습니다.
     */
    void saveUser(User users);

    /**
     * 사용자 SEQ 정보를 통해 데이터베이스를 조회합니다.
     * @param manageSeq 사용자 저장 정보
     * @return 사용자 정보 값을 반환합니다.
     */
    User findByManageSeq(Long manageSeq) ;

    /**
     * 사용자 정보를 데이터 베이스에서 모두 가져옵니다.
     * @return List 형식으로 반환됩니다.
     */
    List<User> findAllMember() ;

    /**
     * 사용자 정보를 ID 값을 통해 조회합니다.
     * @param userID 사용자 아이디
     * @return 조회된 값을 반환합니다.
     */
    User findByLoginId(String userID) ;

    /**
     * 사용자 정보를 업데이트 합니다.
     * @param userSEQ 사용자 SEQ
     * @param updatedUserModel 새로운 정보를 갖고있는 유저 정보
     */
    void updateUser(Long userSEQ, User updatedUserModel) ;

    /**
     * 사용자 정보를 업데이트 합니다.
     * @param userID 사용자 ID
     * @param updatedUserModel 새로운 정보를 갖고있는 유저 정보
     */
    void updateUser(String userID, User updatedUserModel) ;


    /**
     * 사용자를 데이터 베이스에서 삭제합니다.
     * @param userSeq 사용자 SEQ
     */
    void removeUserSEQ(Long userSeq);

    /**
     * 사용자를 데이터 베이스에서 삭제합니다.
     * @param userID 사용자 아이디
     */
    void removeUserID(String userID);

}
