package com.codefans.springcloud.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author: codefans
 * @date: 2018-06-22 09:47
 */
public class JedisClientBase {

    protected final String REDIS_RESULT_OK = "OK";

    protected String host = "";
    protected int port = 0;
    protected String password;
    protected int connectionTimeout;
    protected int soTimeout;

    //最大重试次数
    int maxAttempts;

    GenericObjectPoolConfig poolConfig;
    //最小空闲连接数
    int minIdle = 0;
    //最大空闲连接数
    int maxIdle = 8;
    //最大连接数
    int maxTotal = 8;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public GenericObjectPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    void print(List<String> list) {
        for(String str : list) {
            System.out.println(str);
        }
    }

    void print(Set<String> keys) {
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }
    }


}
