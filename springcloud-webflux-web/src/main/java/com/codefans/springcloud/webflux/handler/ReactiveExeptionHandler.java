package com.codefans.springcloud.webflux.handler;

import com.alibaba.fastjson.JSON;
import com.codefans.springcloud.webflux.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @Author: codefans
 * @Date: 2022-08-03 16:46
 */

@Slf4j
@Component
@Order(-2)
public class ReactiveExeptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        log.error("process system error", throwable);
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer wrap = response.bufferFactory().wrap(JSON.toJSONString(Result.fail(throwable.getMessage())).getBytes());
        return response.writeWith(Mono.just(wrap));
    }
}

