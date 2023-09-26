package com.codefans.springcloud.dto;

import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Future;

/**
 * @Author: codefans
 * @Date: 2022-08-23 18:25
 */

public class ResponseDto {

    public ResponseDto(Future<HttpResponse> httpResponseFuture, HttpServletResponse response) {
        this.httpResponseFuture = httpResponseFuture;
        this.response = response;
    }

    Future<HttpResponse> httpResponseFuture;

    HttpServletResponse response;

    public Future<HttpResponse> getHttpResponseFuture() {
        return httpResponseFuture;
    }

    public void setHttpResponseFuture(Future<HttpResponse> httpResponseFuture) {
        this.httpResponseFuture = httpResponseFuture;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
