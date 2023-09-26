package com.codefans.web.controller;

import com.codefans.rpc.utils.TraceUtils;
import com.codefans.web.service.CustomerService;
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
public class CustomerController {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private CustomerService customerService;

    @GetMapping("/queryCustomer")
    public String queryUser(String userId) {
        String userInfo = null;
        userInfo = customerService.query(userId);
        logger.info("queryCustomer, userId={}, userInfo={}, traceId={}", userId, userInfo, TraceUtils.getTraceId());
        return userInfo;
    }
}
