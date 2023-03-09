package com.space.haramspring.user.repository.memory;

import com.space.haramspring.core.usecase.users.repository.DeleteUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemoryDeleteUserUseCase implements DeleteUserUseCase {

    @Override
    public boolean deleteById(String userId) {

        if (!UserMemoryStore.store.containsKey(userId)) {
            return false;
        }

        UserMemoryStore.store.remove(userId);
        return true;
    }
}
