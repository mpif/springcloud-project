package com.codefans.rpc.utils;

import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-28 18:20
 */

public class TraceUtils {

    public final static String TRACE_ID = "traceId";

    /**
     * 初始化traceId,由consumer调用
     */
    public static void initTrace() {
        String traceId = generateTraceId();
        putTraceIdToMDC(traceId);
    }

    /**
     * 从Dubbo中获取traceId，provider调用
     * @param context
     */
    public static void getTraceInfo(RpcContext context) {
        String traceId = (String) context.getAttachment(TRACE_ID);
        if (!StringUtils.hasText(traceId)) {
            traceId = generateTraceId();
        }
        putTraceIdToMDC(traceId);
    }

    /**
     * 把traceId放入dubbo远程调用中，consumer调用
     * @param context
     */
    public static void putTraceInto(RpcContext context) {
        String traceId = getTraceIdFromMDC();
        if (!StringUtils.hasText(traceId)) {
            traceId = generateTraceId();
        }
        context.setAttachment(TRACE_ID, traceId);
        putTraceIdToMDC(traceId);
    }

    /**
     * 从MDC中清除traceId
     */
    public static void clearTrace() {
        MDC.remove(TRACE_ID);
    }

    /****************************私有方法区*********************************/

    /**
     * 从MDC中获取traceId
     * */
    public static String getTraceIdFromMDC() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 将traceId放入MDC
     * @param traceId
     */
    private static void putTraceIdToMDC(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 生成traceId
     * @return
     */
    private static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
