package com.space.haramspring.core.usecase.users.repository;

import com.space.haramspring.core.entity.users.User;

public interface GetUserInfoUseCase {

    User findById(String userid);

//    User findByName();
//
//    User findBySequenceNumber();
//
//    User findByEmail();
}
