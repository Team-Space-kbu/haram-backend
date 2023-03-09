package com.space.haramspring.user.repository.memory;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.core.usecase.users.repository.AddUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.space.haramspring.user.repository.memory.UserMemoryStore.store;

@Component
@Slf4j
public class MemoryAddUserUseCase implements AddUserUseCase {

    @Override
    public User addUser(User user) {

        log.info("[MemoryAddUser - addUser] {} 해당 유저 객체 저장", user);
        String userId = user.getId();
        store.put(userId, user);

        User saveUser = store.get(userId);
        log.info("[MemoryAddUser - addUser] {} 저장된 유저 객체", saveUser);
        return saveUser;
    }
}
