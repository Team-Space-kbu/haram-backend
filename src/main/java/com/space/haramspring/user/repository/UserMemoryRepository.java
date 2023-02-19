package com.space.haramspring.user.repository;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.core.usecase.users.repository.AddUserUseCase;
import com.space.haramspring.core.usecase.users.repository.DeleteUserUseCase;
import com.space.haramspring.core.usecase.users.repository.GetUserInfoUseCase;
import com.space.haramspring.core.usecase.users.repository.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserMemoryRepository implements UserRepository {

    private final GetUserInfoUseCase getUserInfoUseCase;
    private final AddUserUseCase addUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public User getUserInfoByUserId(String userId) {

        log.info("[UserMemoryRepository - getUserInfoByUserId] 유스케이스에게 {} 유저 정보 요청", userId);
        User user = getUserInfoUseCase.findById(userId);
        return user;
    }

    @Override
    public User addUserByUserObject(User user) {

        log.info("[UserMemoryRepository - addUserByUserObject] 유스케이스에게 {} 유저 정보 저장 요청", user);
        User saveUser = addUserUseCase.addUser(user);
        return saveUser;
    }

    @Override
    public User updateUserByUserObject(User user) {

        log.info("[UserMemoryRepository - updateUserByUserObject] 유스케이스에게 변경된 유저 정보 변경 요청 {}", user);
        User newUser = updateUserUseCase.updateByUserObject(user);
        return newUser;
    }

    @Override
    public Boolean deleteUserByUserId(String userId) {

        log.info("[UserMemoryRepository - deleteUserByUserId] 유스케이스에게 유저 삭제 요청 {}", userId);
        Boolean isSuccess = deleteUserUseCase.deleteById(userId);
        return isSuccess;
    }
}
