package com.codefans;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class SpringCloudMvcWebStartup {
    public static void main(String[] args) {
        System.out.println("----------SpringCloudMvcWebStartup begin----------");
        SpringApplication.run(SpringCloudMvcWebStartup.class, args);
        System.out.println("----------SpringCloudMvcWebStartup success----------");
    }
}