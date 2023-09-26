package com.codefans.rpc.filter;

import com.codefans.rpc.utils.TraceUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-28 18:18
 */
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class DubboTraceIdFilter implements Filter {

    /**
     *
     */
    private Logger logger = LoggerFactory.getLogger(DubboTraceIdFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        MDC.clear();
        Result result = null;
        try {
            String fullName = invoker.getInterface().getName() + "." + invocation.getMethodName();
            RpcContext context = RpcContext.getContext();
            if (context.isConsumerSide()) {
                TraceUtils.putTraceInto(context);
                logger.info("DubboTraceIdFilter consumer side, 请求方法={}, traceId={}", fullName, TraceUtils.getTraceIdFromMDC());
            } else if (context.isProviderSide()) {
                TraceUtils.getTraceInfo(context);
                logger.info("DubboTraceIdFilter provider side, 请求方法={}, traceId={}", fullName, TraceUtils.getTraceIdFromMDC());
            }
            result = invoker.invoke(invocation);
        } catch (Exception e) {
            logger.error("设置traceId异常", e);
        } finally {
//            MDC.clear();
        }
        return result;
    }
}
