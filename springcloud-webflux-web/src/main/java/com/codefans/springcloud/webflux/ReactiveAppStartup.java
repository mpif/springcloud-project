package com.codefans.springcloud.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:04
 */
@EnableR2dbcAuditing
@SpringBootApplication(scanBasePackages={"com.codefans.springcloud"})
//@EnableMongoRepositories(basePackageClasses = CustomerRepository.class)
//@EnableSpringDataWebSupport
@EnableR2dbcRepositories
public class ReactiveAppStartup {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveAppStartup.class, args);
    }

}
