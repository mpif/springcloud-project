package com.codefans.springcloud.webflux.runner;

import com.netflix.zuul.*;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.filters.MutableFilterRegistry;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @Author: codefans
 * @Date: 2022-08-08 23:39
 */
@Component
@Order(value = 1)
public class GroovyLoadLineRunner implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {

        FilterRegistry filterRegistry = new MutableFilterRegistry();
        FilterFactory filterFactory = new DefaultFilterFactory();
        DynamicFilterLoader dynamicFilterLoader = new DynamicFilterLoader(filterRegistry, new GroovyCompiler(), filterFactory);
//        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        //读取配置,获取脚本根目录
        String scriptRoot = System.getProperty("zuul.filter.root", "groovy/filters");
        //获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
        if (scriptRoot.length() > 0) {
            scriptRoot = scriptRoot + File.separator;
        }

        String[] directories = new String[]{scriptRoot + "pre", scriptRoot + "route", scriptRoot + "post"};
        String[] classNames = new String[]{};
        int pollingIntervalSeconds = Integer.parseInt(refreshInterval);
        FilenameFilter filenameFilter = new GroovyFileFilter();
        FilterFileManager.FilterFileManagerConfig config = new FilterFileManager.FilterFileManagerConfig(directories, classNames, pollingIntervalSeconds, filenameFilter);
        FilterFileManager filterFileManager = new FilterFileManager(config, dynamicFilterLoader);
        filterFileManager.init();
    }
}