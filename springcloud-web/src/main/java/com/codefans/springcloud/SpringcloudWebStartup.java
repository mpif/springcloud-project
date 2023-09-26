package com.codefans.springcloud;

import com.codefans.springcloud.controller.CustomController;
import com.codefans.springcloud.dto.ResponseDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:04
 */
@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class SpringcloudWebStartup {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudWebStartup.class, args);
    }

}
