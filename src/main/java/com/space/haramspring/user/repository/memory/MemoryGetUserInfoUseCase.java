package com.space.haramspring.user.repository.memory;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.core.usecase.users.repository.GetUserInfoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemoryGetUserInfoUseCase implements GetUserInfoUseCase {

    @Override
    public User findById(String userId) {

        log.info("[MemoryGetUserInfo - findById] {} 해당 id로 유저 객체 조회", userId);
        User user = UserMemoryStore.store.get(userId);
        log.info("[MemoryGetUserInfo - findById] {} 조회된 유저 객체 반환", user);
        return user;
    }

//    @Override
//    public User findByName() {
//        return null;
//    }
//
//    @Override
//    public User findBySequenceNumber() {
//        return null;
//    }
//
//    @Override
//    public User findByEmail() {
//        return null;
//    }
}
