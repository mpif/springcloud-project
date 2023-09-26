package com.codefans.springcloud.alibaba.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:04
 */

@EnableJpaAuditing
@SpringBootApplication
public class SpringcloudAlibabaWebStartup {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAlibabaWebStartup.class, args);
    }

}
