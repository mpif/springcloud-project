package com.codefans.web.service;

import com.codefans.rpc.api.CustomerRpcApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-27 21:00
 */
@Service
public class CustomerService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @DubboReference(group = "jifang1", version = "1.0", check = false)
    private CustomerRpcApi customerRpcApi;

    public String query(String id) {
        String result = customerRpcApi.query(id);
        logger.info("CustomerService.query(), id={}, result={}", id, result);
        return result;
    }
}
