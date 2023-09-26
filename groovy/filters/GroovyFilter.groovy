package groovy.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.apache.catalina.servlet4preview.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

class GroovyFilter  extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyFilter.class)

    @Override
    String filterType() {
        return FilterConstants.PRE_TYPE
    }

    //过滤器优先级
    @Override
    int filterOrder() {
        return 5
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        HttpServletRequest request = (HttpServletRequest) RequestContext.getCurrentContext().getRequest()
        Enumeration<String> headerNames = request.getHeaderNames()
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement()
            String value = request.getHeader(name)
            LOGGER.info("header: " + name + ":" + value)
        }
        LOGGER.info("This is Groovy Filter")
        return null
    }

}