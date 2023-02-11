package com.space.haramspring.user.service;

import com.space.haramspring.core.usecase.users.AddUser;
import com.space.haramspring.core.usecase.users.DeleteUser;
import com.space.haramspring.core.usecase.users.GetUserInfo;
import com.space.haramspring.core.usecase.users.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInf{

    final GetUserInfo getInfoUser;
    final AddUser addUser;
    final UpdateUser updateUser;
    final DeleteUser deleteUser;


    public Map<String, String> addUser() {

        return null;
    }
}
