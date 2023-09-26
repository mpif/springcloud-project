package com.codefans.springcloud.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:32
 */



// 统一日志记录拦截器
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        //统一打印日志
        String requestURL = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        //原始请求地址
        String ip = httpServletRequest.getLocalAddr();
        //请求参数
        String params = httpServletRequest.getQueryString();
        if("GET".equalsIgnoreCase(method) && StringUtils.isNoneBlank(params)) {
            log.info("IP：{}，method：{}，url：{}?{}", ip, method, requestURL, params);
        } else {
            log.info("IP：{}，method：{}，url：{}", ip, method, requestURL);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
