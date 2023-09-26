package com.codefans.web.config;

import com.codefans.web.filter.WebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: ShengzhiCai
 * @Date: 2023-08-28 15:44
 */
@Configuration
public class FilterConfig {

    @Resource
    private WebFilter webFilter;

    @Bean
    public FilterRegistrationBean generateFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(webFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }
}
