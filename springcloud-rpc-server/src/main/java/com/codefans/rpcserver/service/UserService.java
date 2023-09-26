//package com.codefans.rpcserver.service;
//
//import com.codefans.rpcprovider.rpc.UserRpcApi;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.stereotype.Service;
//
///**
// * @Author: ShengzhiCai
// * @Date: 2023-08-27 20:54
// */
//@Service
//public class UserService {
//
//    @DubboReference
//    private UserRpcApi userRpcApi;
//
//    public String queryUser(String userId) {
//        return "springcloud-rpc-server, 用户信息userId=" + userId;
//    }
//
//}
