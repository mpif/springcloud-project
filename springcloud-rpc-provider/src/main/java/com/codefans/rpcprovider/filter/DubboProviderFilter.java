package com.codefans.rpcprovider.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-27 21:24
 */
@Activate(group = {CommonConstants.PROVIDER})
public class DubboProviderFilter implements Filter {

    /**
     *
     */
    private Logger logger = LoggerFactory.getLogger(DubboProviderFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        MDC.clear();
        Result result = null;
        try {
            String traceId = invocation.getAttachment("traceId");
            if(!StringUtils.hasText(traceId)) {
                traceId = UUID.randomUUID().toString().replaceAll("-", "");
            }
            MDC.put("traceId", traceId);
            String fullName = invoker.getInterface().getName() + "." + invocation.getMethodName();
            logger.info("rpcprovider dubbo provider filter, 请求方法={}, traceId={}", fullName, traceId);
            result = invoker.invoke(invocation);
            logger.info("rpcprovider dubbo provider filter, finished");
        } catch (Exception e) {
            logger.error("设置traceId异常", e);
        } finally {
            MDC.clear();
        }
        return result;
    }
}
