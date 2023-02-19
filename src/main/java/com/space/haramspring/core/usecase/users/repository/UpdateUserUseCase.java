package com.space.haramspring.core.usecase.users.repository;


import com.space.haramspring.core.entity.users.User;

public interface UpdateUserUseCase {

    User updateByUserObject(User user);
}