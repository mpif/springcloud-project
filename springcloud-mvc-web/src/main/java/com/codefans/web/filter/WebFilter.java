package com.codefans.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-27 21:25
 */
@Component
public class WebFilter extends OncePerRequestFilter {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(WebFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader("traceId");
            if(!StringUtils.hasText(traceId)) {
                traceId = UUID.randomUUID().toString().replaceAll("-", "");
            }
            MDC.put("traceId", traceId);
            logger.info("WebFilter, 设置traceId={}, time={}", traceId, new Date());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("设置traceId异常", e);
        } finally {
            MDC.clear();
        }
    }
}
