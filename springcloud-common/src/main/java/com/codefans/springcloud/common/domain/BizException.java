package com.codefans.springcloud.common.domain;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:08
 */

public class BizException extends RuntimeException{
    public BizException(){super();}

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable cause){
        super(message,cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

