package com.codefans.rpcserver.provider.impl;

import com.codefans.rpc.api.CustomerRpcApi;
import com.codefans.rpc.api.UserRpcApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-27 20:57
 */
@DubboService(group = "jifang1", version = "1.0")
public class CustomerRpcImpl implements CustomerRpcApi {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerRpcImpl.class);

    @DubboReference(group = "jifang1", version = "1.0", check = false)
    private UserRpcApi userRpcApi;

    @Override
    public String query(String id) {
        String userInfo = userRpcApi.queryUser(id);
        String result = "客户id=" + id + ", userInfo=" + userInfo + ", time=" + new Date();
        logger.info("customerRpcImpl.query(), id={}, result={}", id, result);
        return result;
    }
}
