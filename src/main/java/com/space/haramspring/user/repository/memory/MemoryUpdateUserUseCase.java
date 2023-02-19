package com.space.haramspring.user.repository.memory;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.core.usecase.users.repository.UpdateUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemoryUpdateUserUseCase implements UpdateUserUseCase {

    @Override
    public User updateByUserObject(User user) {

        String userId = user.getId();
        UserMemoryStore.store.put(userId, user);

        User newUser = UserMemoryStore.store.get(userId);
        return newUser;
    }
}
