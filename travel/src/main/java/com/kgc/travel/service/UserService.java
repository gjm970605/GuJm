package com.kgc.travel.service;

import com.kgc.travel.domain.ResultInfo;
import com.kgc.travel.domain.User;

public interface UserService {
    ResultInfo register(User user);


    boolean active(String toActiveCode);

    ResultInfo login(String username, String password);
}
