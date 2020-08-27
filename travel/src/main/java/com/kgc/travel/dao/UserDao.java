package com.kgc.travel.dao;

import com.kgc.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);

    boolean addUser(User user);

    User findByCode(String toActiveCode);

    boolean active(User findUser);
}
