package com.codefans.springcloud.schedule;

import com.codefans.springcloud.controller.CustomController;
import com.codefans.springcloud.dto.ResponseDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: codefans
 * @Date: 2022-08-24 8:29
 */
@Component
public class ResponseScheduler {

    /**
     *
     */
    private Logger log = LoggerFactory.getLogger(ResponseScheduler.class);

    @PostConstruct
    public void startup() {
        int corePoolSize = 1;
        ThreadFactory namedThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "responseWriteThreadPool");
            }
        };
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize, namedThreadFactory);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<ResponseDto> list = CustomController.list;
                Iterator<ResponseDto> iter = list.iterator();
                ResponseDto responseDto = null;
                Future<HttpResponse> httpResponseFuture = null;
                HttpResponse httpResponse = null;
                HttpServletResponse httpServletResponse = null;
                while(iter.hasNext()) {
                    responseDto = iter.next();
                    httpResponseFuture = responseDto.getHttpResponseFuture();
                    httpServletResponse = responseDto.getResponse();
                    try {
                        httpResponse = httpResponseFuture.get(3000, TimeUnit.MILLISECONDS);
                        HttpEntity entity = httpResponse.getEntity();
                        ajaxJson(entity != null ? EntityUtils.toString(entity) : null, httpServletResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    iter.remove();
                }
            }
        }, 1000, 10, TimeUnit.MILLISECONDS);
        log.info("ResponseSchedule.startup() success!!!");
    }

    public void ajaxJson(String jsonString, HttpServletResponse response) {
        ajax(jsonString, "application/json",response);
    }
    public void ajax(String content, String type,HttpServletResponse response) {
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Length", content.getBytes(StandardCharsets.UTF_8).length+"");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
