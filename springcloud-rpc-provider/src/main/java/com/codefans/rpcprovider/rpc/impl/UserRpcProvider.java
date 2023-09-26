package com.codefans.rpcprovider.rpc.impl;

import com.codefans.rpc.api.UserRpcApi;
import com.codefans.rpc.utils.TraceUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-26 22:44
 */
//@Service
@DubboService(group = "jifang1",version = "1.0")
public class UserRpcProvider implements UserRpcApi {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserRpcProvider.class);

    public String queryUser(String userId) {
        String result = "userId=[" + userId + "]";
        logger.info("UserRpcProvider.queryUser(), userId={}, result={}, traceId={}", userId, result, TraceUtils.getTraceId());
        return result;
    }
}
