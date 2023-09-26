package com.codefans.springcloud.feign;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @Author: codefans
 * @Date: 2022-09-21 18:20
 */

public class OkHttpJsonFeign extends OkHttpFeign {
    private static final Builder BUILDER = build();
    private static final Encoder ENCODER = new JacksonEncoder();
    private static final Decoder DECODER = new JacksonDecoder();

    public OkHttpJsonFeign() {
    }

    public static Builder build() {
//        return OkHttpFeign.build().encoder(ENCODER).decoder(DECODER);
        return OkHttpFeign.build();
    }

    public static <T> T target(Class<T> apiType, String host) {
        return build().target(apiType, host);
    }
}
