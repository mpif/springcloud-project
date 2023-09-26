package com.codefans.springcloud.service.impl;

import com.codefans.springcloud.feign.OkHttpJsonFeign;
import com.codefans.springcloud.service.UserService;

/**
 * @Author: codefans
 * @Date: 2022-09-21 18:16
 */
public class UserServiceImpl implements UserService {

    private String host;

    public UserServiceImpl(String host) {
        this.host = host;
    }

    @Override
    public String getUserName(String id) {
        UserService userService= OkHttpJsonFeign.target(UserService.class, this.host);
        return userService.getUserName(id);
    }


}
