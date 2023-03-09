package com.space.haramspring.user.repository.memory;

import com.space.haramspring.core.entity.users.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class UserMemoryStore {

    /**
     * User 정보 저장소
     * spring이 자동으로 제공하는 멀티스레드에서의 접근에서 충돌을 막기 위해
     * HashMap 이 아닌 ConcurrentHashMap 을  사용.
     */
    static Map<String, User> store = new ConcurrentHashMap<>();

    public static void clearStore() {

        store.clear();
    }
};

