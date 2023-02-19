package com.space.haramspring.user.service;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserInfoByRepository(String userId) {

        log.info("[UserServiceImpl - getUserInfoByRepository] 레포지토리에게 {} 정보 요청", userId);
        User user = userRepository.getUserInfoByUserId(userId);
        return user;
    }

    @Override
    public User addUserByRepository(User user) {

        log.info("[UserServiceImpl - addUserByRepository] 레포지토리에게 유저 정보 저장 요청 {}", user);
        User saveUser = userRepository.addUserByUserObject(user);
        return saveUser;
    }

    @Override
    public User updateUserByRepository(User user) {

        log.info("[UserServiceImpl - updateUserByRepository] 레포지토리에게 유저 정보 변경 요청 {}", user);
        User newUser = userRepository.updateUserByUserObject(user);
        return newUser;
    }

    @Override
    public Boolean deleteUserByRepository(String userId) {

        log.info("[UserServiceImpl - deleteUserByRepository] 레포지토리에게 {} 유저 정보 삭제 요청 ", userId);
        Boolean isSuccess = userRepository.deleteUserByUserId(userId);
        return isSuccess;
    }
}
