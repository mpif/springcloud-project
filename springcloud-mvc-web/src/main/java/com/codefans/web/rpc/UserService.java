package com.codefans.web.rpc;

import com.codefans.rpc.api.UserRpcApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-26 22:43
 */
@Service
public class UserService {

    @DubboReference(group = "jifang1", version = "1.0", check = false)
    private UserRpcApi userRpcApi;

    public String queryUser(String userId) {
        return userRpcApi.queryUser(userId);
    }
}
