package com.codefans.rpcserver.filter;

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
@Activate(group = {CommonConstants.CONSUMER})
public class DubboConsumerFilter implements Filter {

    /**
     *
     */
    private Logger logger = LoggerFactory.getLogger(DubboConsumerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        try {

            String traceId = MDC.get("traceId");
            if(!StringUtils.hasText(traceId)) {
                traceId = UUID.randomUUID().toString().replaceAll("-", "");
            }
            invocation.setAttachment("traceId", traceId);
            String fullName = invoker.getInterface().getName() + "." + invocation.getMethodName();
            logger.info("rpcserver dubbo consumer filter, 请求方法={}, traceId={}", fullName, traceId);
            result = invoker.invoke(invocation);
            logger.info("rpcserver dubbo consumer filter, finished");
        } catch (Exception e) {
            logger.error("设置traceId异常", e);
        } finally {
            MDC.clear();
        }
        return result;
    }

}
