package com.codefans.web.controller;

import com.codefans.rpc.utils.TraceUtils;
import com.codefans.web.rpc.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-26 22:42
 */
@RestController
public class UserController {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping("/queryUser")
    public String queryUser(String userId) {
        String userInfo = null;
        userInfo = userService.queryUser(userId);
        logger.info("queryUser(), userId={}, userInfo={}, traceId={}", userId, userInfo, TraceUtils.getTraceId());
        return userInfo;
    }
}
