package com.codefans.springcloud.alibaba.web.controller;

import com.alibaba.fastjson.JSON;
import com.codefans.springcloud.alibaba.web.dto.ResponseDto;
import com.codefans.springcloud.alibaba.web.util.HttpUtils;
import com.codefans.springcloud.common.domain.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: codefans
 * @Date: 2022-08-02 17:34
 */
@RestController
@RequestMapping("/custom")
public class CustomController {

    /**
     *
     */
    private Logger log = LoggerFactory.getLogger(CustomController.class);

    private static final String redirectUrl = "http://localhost:8088/rpcTest";

    public static List<ResponseDto> list = new CopyOnWriteArrayList<>();

    private AtomicLong syncConCurrentCount = new AtomicLong(0);
    private AtomicLong asyncConCurrentCount = new AtomicLong(0);

    @PostMapping(value = "/redirectHttp")
    public ResponseEntity<Void> redirectHttp(@RequestParam Map<String,String> input){
        log.info("redirectHttp called, input={}, time={}", JSON.toJSONString(input));
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }

    @PostMapping("/redirectRequest")
    public ModelAndView redirectRequest(ModelMap model) {
        log.info("redirectRequest called, time={}", new Date());
//        return new ModelAndView("redirect:" + redirectUrl, model);
        /**
         * redirect是发送get请求
         */
        return new ModelAndView("redirect:/custom/redirectMethod", model);
    }
    @PostMapping("/redirectMethod")
    public Result redirectMethod(ModelMap model) {
        log.info("redirectMethod called, time={}", new Date());
        return Result.success("hello redirectMethod");
    }
    @PostMapping("/forwardHttp")
    public ModelAndView forwardHttp(ModelMap model) {
        log.info("forwardHttp called, time={}", new Date());
//        return new ModelAndView("forward:" + redirectUrl, model);
        /**
         * forward是发送post请求
         */
        return new ModelAndView("forward:/custom/forwardedMethod", model);
    }

    @PostMapping("/forwardedMethod")
    public Result forwardedMethod(ModelMap model) {
        log.info("forwardedMethod called, time={}", new Date());
        return Result.success("hello forwardedMethod");
    }

    @PostMapping("/syncHttp")
    public Result syncHttp(@NotNull Long id, HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        try {
            syncConCurrentCount.incrementAndGet();
            HttpUtils.syncRequest();
            log.info("syncHttp(), cost=[{}ms], concurrent=[{}]", (System.currentTimeMillis() - beginTime), syncConCurrentCount.decrementAndGet());
        } catch (IOException e) {
            log.error("syncRequest()异常", e);
        }

        return Result.success();
    }

    @PostMapping("/asyncHttp")
    public Result asyncHttp(@NotNull Long id) {

        try {
//            HttpUtils.asyncRequestWithReactiveStream();
            long beginTime = System.currentTimeMillis();
            HttpUtils.asyncRequest();
            log.info("asyncRequest(), cost=[{}ms]", (System.currentTimeMillis() - beginTime));
        } catch (Exception e) {
            log.error("asyncRequestWithReactiveStream()异常", e);
        }
        return Result.success();
    }

    @PostMapping("/asyncRequest")
    public Result asyncRequest(HttpServletResponse response) {
        asyncConCurrentCount.incrementAndGet();
        long beginTime = System.currentTimeMillis();
        try {
//            HttpUtils.asyncRequestWithReactiveStream();
            Future<HttpResponse> httpResponseFuture = HttpUtils.asyncReqWithResponse();
            HttpResponse httpResponse = httpResponseFuture.get(3, TimeUnit.SECONDS);
            HttpEntity entity = httpResponse.getEntity();
            String responseContent = entity != null ? EntityUtils.toString(entity) : null;

            log.info("asyncReqWithResponse(), cost=[{}ms], concurrent=[{}]", (System.currentTimeMillis() - beginTime), asyncConCurrentCount.decrementAndGet());

            return Result.success(responseContent);

//            list.add(new ResponseDto(httpResponseFuture, response));
        } catch (Exception e) {
            log.error("asyncRequestWithReactiveStream()异常", e);
        }
        log.info("return default result!, cost=[{}ms]", (System.currentTimeMillis() - beginTime));
        return Result.success();
    }



}
