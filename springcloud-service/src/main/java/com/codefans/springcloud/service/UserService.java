package com.codefans.springcloud.service;

import feign.Headers;
import feign.RequestLine;

public interface UserService {
    /**
     * 登录接口
     */
    @RequestLine("POST /http/getMsg")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    String getUserName(String id);
}
