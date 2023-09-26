package com.codefans.springcloud.controller;

import com.codefans.springcloud.service.UserService;
import com.codefans.springcloud.service.impl.UserServiceImpl;
import com.codefans.springcloud.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: codefans
 * @Date: 2022-09-20 8:55
 */
@RestController
@RequestMapping("/http/performance")
public class HttpPerformanceController {

    private Logger log = LoggerFactory.getLogger(HttpPerformanceController.class);

    private AtomicLong restTemplateCount = new AtomicLong(0);
    private long restTemplateMax = 0;
    private AtomicLong httpClientSyncCount = new AtomicLong(0);
    private long httpClientSyncMax = 0;
    private AtomicLong httpClientAsyncCount = new AtomicLong(0);
    private long httpClientAsyncMax = 0;

    private AtomicLong okHttpSyncCount = new AtomicLong(0);
    private long okHttpSyncMax = 0;

    private AtomicLong okHttpAsyncCount = new AtomicLong(0);
    private long okHttpAsyncMax = 0;

    private AtomicLong googleHttpClientSyncCount = new AtomicLong(0);
    private long googleHttpClientSyncMax = 0;

    private AtomicLong googleHttpClientAsyncCount = new AtomicLong(0);
    private long googleHttpClientAsyncMax = 0;

    private String url = "http://localhost:8088/http/getMsg";

    private RestTemplate restTemplate;
    private UserService userService;

    public HttpPerformanceController() {
        restTemplate = new RestTemplate();
        userService = new UserServiceImpl("http://localhost:8088");
    }

    @PostMapping("/restTemplate")
    public String restTemplate(HttpServletRequest request) {
        restTemplateCount.incrementAndGet();

        Map<String, String> uriVariables = new HashMap<>(4);
        Class<String> cls = String.class;
        HttpEntity reqEntity = new HttpEntity("id=myId123");
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, cls, uriVariables);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, reqEntity, cls, uriVariables);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            restTemplateMax = restTemplateCount.decrementAndGet() > restTemplateMax ? restTemplateCount.get() : restTemplateMax;
        }
        log.info("restTemplate写性能测试, 返回值:{}, 当前并发数为:{}, 最大并发数:{}", responseEntity.getBody(), restTemplateCount.get(), restTemplateMax);
        return "restTemplate写性能测试, 返回值:" + responseEntity.getBody() + ", 当前并发数为:" + restTemplateCount.get();
    }

    @PostMapping("/openFeign")
    public String openFeign(HttpServletRequest request) {
        restTemplateCount.incrementAndGet();
        String id = request.getParameter("id");
        String userName = userService.getUserName(id);
        log.info("openFeign--->id={}, userName={}", id, userName);
        restTemplateMax = restTemplateCount.decrementAndGet() > restTemplateMax ? restTemplateCount.get() : restTemplateMax;
        log.info("openFeign写性能测试, 当前并发数为:{}, 最大并发数:{}", restTemplateCount.get(), restTemplateMax);
        return "openFeign写性能测试, 当前并发数为:" + restTemplateCount.get();
    }


    @PostMapping("/httpClientSync")
    public String httpClientSync(HttpServletRequest request) {
        httpClientSyncCount.incrementAndGet();
        try {
            HttpUtils.syncRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpClientSyncMax = httpClientSyncCount.decrementAndGet() > httpClientSyncMax ? httpClientSyncCount.get() : httpClientSyncMax;
        log.info("httpClientSync写性能测试, 当前并发数为:{}, 最大并发数:{}", restTemplateCount.get(), httpClientSyncMax);
        return "httpClientSync写性能测试, 当前并发数为:" + httpClientSyncCount.get();
    }

    @PostMapping("/httpClientAsync")
    public String httpClientAsync(HttpServletRequest request) {
        httpClientAsyncCount.incrementAndGet();
        try {
            HttpUtils.asyncRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpClientAsyncMax = httpClientAsyncCount.decrementAndGet() > httpClientAsyncMax ? httpClientAsyncCount.get() : httpClientAsyncMax;
        log.info("httpClientAsync写性能测试, 当前并发数为:{}, 最大并发数:{}", httpClientAsyncCount.get(), httpClientAsyncMax);
        return "httpClientAsync写性能测试, 当前并发数为:" + httpClientAsyncCount.get();
    }

    @PostMapping("/okHttpSync")
    public String okHttpSync(HttpServletRequest request) {
        okHttpSyncCount.incrementAndGet();
        HttpUtils.okHttpSync();
        okHttpSyncMax = okHttpSyncCount.decrementAndGet() > okHttpSyncMax ? okHttpSyncCount.get() : okHttpSyncMax;
        log.info("okHttpSync写性能测试, 当前并发数为:{}, 最大并发数:{}", okHttpSyncCount.get(), okHttpSyncMax);
        String maxReset = request.getParameter("maxReset");
        if("true".equalsIgnoreCase(maxReset)) {
            okHttpSyncMax = 0;
            log.info("okHttpAsync写性能测试, 最大并发数重置为:{}", okHttpSyncMax);
        }
        return "okHttpSync写性能测试, 当前并发数为:" + okHttpSyncCount.get();
    }

    @PostMapping("/okHttpAsync")
    public String okHttpAsync(HttpServletRequest request) {
        okHttpAsyncCount.incrementAndGet();
        HttpUtils.okHttpAsync();
        okHttpAsyncMax = okHttpAsyncCount.decrementAndGet() > okHttpAsyncMax ? okHttpAsyncCount.get() : okHttpAsyncMax;
        log.info("okHttpAsync写性能测试, 当前并发数为:{}, 最大并发数:{}", okHttpAsyncCount.get(), okHttpAsyncMax);
        String maxReset = request.getParameter("maxReset");
        if("true".equalsIgnoreCase(maxReset)) {
            okHttpAsyncMax = 0;
            log.info("okHttpAsync写性能测试, 最大并发数重置为:{}", okHttpAsyncMax);
        }
        return "okHttpAsync写性能测试, 当前并发数为:" + okHttpAsyncCount.get();
    }
    @PostMapping("/googleHttpClientSync")
    public String googleHttpClientSync(HttpServletRequest request) {
        googleHttpClientSyncCount.incrementAndGet();
        String id = request.getParameter("id");
        String userName = userService.getUserName(id);
        log.info("googleHttpClientSync--->id={}, userName={}", id, userName);
        googleHttpClientSyncMax = googleHttpClientSyncCount.decrementAndGet() > googleHttpClientSyncMax ? googleHttpClientSyncCount.get() : googleHttpClientSyncMax;
        log.info("googleHttpClientSync写性能测试, 当前并发数为:{}, 最大并发数:{}", googleHttpClientSyncCount.get(), googleHttpClientSyncMax);
        return "googleHttpClientSync写性能测试, 当前并发数为:" + googleHttpClientSyncCount.get();
    }

    @PostMapping("/googleHttpClientAsync")
    public String googleHttpClientAsync(HttpServletRequest request) {
        googleHttpClientAsyncCount.incrementAndGet();
        String id = request.getParameter("id");
        String userName = userService.getUserName(id);
        log.info("googleHttpClientAsync--->id={}, userName={}", id, userName);
        googleHttpClientAsyncMax = googleHttpClientAsyncCount.decrementAndGet() > googleHttpClientAsyncMax ? googleHttpClientAsyncCount.get() : googleHttpClientAsyncMax;
        log.info("googleHttpClientAsync写性能测试, 当前并发数为:{}, 最大并发数:{}", googleHttpClientAsyncCount.get(), googleHttpClientAsyncMax);
        return "googleHttpClientAsync写性能测试, 当前并发数为:" + googleHttpClientAsyncCount.get();
    }


}
