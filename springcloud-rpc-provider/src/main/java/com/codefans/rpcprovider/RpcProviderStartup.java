package com.codefans.rpcprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-26 23:03
 */
@SpringBootApplication
@EnableDubbo
public class RpcProviderStartup {
    public static void main(String[] args) {
        System.out.println("------RpcProvider starting------");
        SpringApplication.run(RpcProviderStartup.class, args);
        System.out.println("------RpcProvider startup success------");
    }
}
