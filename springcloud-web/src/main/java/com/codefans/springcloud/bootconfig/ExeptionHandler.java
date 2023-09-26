package com.codefans.springcloud.bootconfig;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:06
 */

import com.codefans.springcloud.common.domain.BizException;
import com.codefans.springcloud.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;


@RestControllerAdvice
@Slf4j
public class ExeptionHandler {
    // validation 参数校验异常处理
    @ExceptionHandler(WebExchangeBindException.class)
    public Result handBindException(WebExchangeBindException e) {
        log.error("process web exchange bind error", e);
        String errMsg = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + "：" + fieldError.getDefaultMessage())
                .reduce(((s1, s2) -> s1 + " \n" + s2))
                .orElse(StringUtils.EMPTY);
        return Result.fail(errMsg);
    }

    // 自定义异常处理
    @ExceptionHandler(BizException.class)
    public Result handBizException(BizException e) {
        log.error("process system error", e);
        return Result.fail(e.getMessage());
    }

    //兜底异常处理
    @ExceptionHandler(Exception.class)
    public Result handException(Exception e) {
        log.error("process system error", e);
        return Result.fail();
    }
}

