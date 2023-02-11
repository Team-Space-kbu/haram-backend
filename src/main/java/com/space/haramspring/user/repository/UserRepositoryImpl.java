package com.space.haramspring.user.repository;

import com.space.haramspring.core.entity.users.User;

import java.util.List;

public class UserRepositoryImpl implements  UserRepository{
    @Override
    public void saveUser(User users) {

    }

    @Override
    public User findByManageSeq(Long manageSeq) {
        return null;
    }

    @Override
    public List<User> findAllMember() {
        return null;
    }

    @Override
    public User findByLoginId(String userID) {
        return null;
    }

    @Override
    public void updateUser(Long userSEQ, User updatedUserModel) {

    }

    @Override
    public void updateUser(String userID, User updatedUserModel) {

    }

    @Override
    public void removeUserSEQ(Long userSeq) {

    }

    @Override
    public void removeUserID(String userID) {

    }
}
