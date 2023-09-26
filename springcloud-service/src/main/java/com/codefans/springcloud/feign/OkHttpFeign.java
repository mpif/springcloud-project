package com.codefans.springcloud.feign;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.Target;
import feign.codec.StringDecoder;
import feign.okhttp.OkHttpClient;

/**
 * @Author: codefans
 * @Date: 2022-09-21 17:57
 */

public class OkHttpFeign extends Feign {
    private static final Builder BUILDER = build();
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    public OkHttpFeign() {
    }

    @Override
    public <T> T newInstance(Target<T> target) {
        return BUILDER.target(target);
    }

    public static Builder build() {
        return Feign.builder().decoder(new StringDecoder())
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .client(OK_HTTP_CLIENT);
    }

    public static <T> T target(Class<T> apiType, String host) {
        return BUILDER.target(apiType, host);
    }
}