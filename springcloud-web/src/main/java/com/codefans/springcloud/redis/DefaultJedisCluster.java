package com.codefans.springcloud.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: codefans
 * @date: 2019-07-11 11:43
 */
public class DefaultJedisCluster {

    private String host;
    private int port;
    private String password;

    private int connectionTimeout;
    private int soTimeout;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
    public int DEFAULT_SO_TIMEOUT = 10000;

    private JedisCluster jedisCluster;

    public DefaultJedisCluster(String host, int port, String password) {
//        host = "127.0.0.1";
//        port = 3793;
//        password = "redisPass123";
        this.host = host;
        this.port = port;
        this.password = password;
        this.connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        this.soTimeout = DEFAULT_SO_TIMEOUT;

//        jedis = new Jedis(host, port, connectionTimeout, soTimeout);
//        jedis.auth(password);

        int maxAttempts = 3;
        int minIdle = 8;
        int maxIdle = 8;
        int maxTotal = 1000;

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        //最大连接数
        poolConfig.setMaxTotal(maxTotal);


        HostAndPort hostAndPort = new HostAndPort(host, port);
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        hostAndPortSet.add(hostAndPort);
//        hostAndPortSet.add(new HostAndPort("127.0.0.1", 3794));
//        hostAndPortSet.add(new HostAndPort("127.0.0.1", 3795));
//        hostAndPortSet.add(new HostAndPort("127.0.0.1", 3796));
//        hostAndPortSet.add(new HostAndPort("127.0.0.1", 3797));
//        hostAndPortSet.add(new HostAndPort("127.0.0.1", 3798));

        jedisCluster = new JedisCluster(hostAndPortSet, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);

    }

    public DefaultJedisCluster(String[] hostArr, int[] portArr, String password) {

//        host = "127.0.0.1";
//        port = 3793;
//        password = "redisPass123";
        this.password = password;
        this.connectionTimeout = 10000;
        this.soTimeout = 10000;

        int maxAttempts = 3;
        int minIdle = 8;
        int maxIdle = 8;
        int maxTotal = 1000;

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        //最大连接数
        poolConfig.setMaxTotal(maxTotal);

        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        for(int i = 0; i < hostArr.length; i ++) {
            hostAndPortSet.add(new HostAndPort(hostArr[i], portArr[i]));
        }

        jedisCluster = new JedisCluster(hostAndPortSet, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);


    }

    public DefaultJedisCluster(String[] addrArr, String password) {

//        host = "127.0.0.1";
//        port = 3793;
//        password = "redisPass123";
        this.password = password;
        this.connectionTimeout = 10000;
        this.soTimeout = 10000;

        int maxAttempts = 3;
        int minIdle = 8;
        int maxIdle = 8;
        int maxTotal = 1000;

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        //最大连接数
        poolConfig.setMaxTotal(maxTotal);

        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        String addr = "";
        String[] hostPort = null;
        for(int i = 0; i < addrArr.length; i ++) {
            addr = addrArr[i];
            hostPort = addr.split(":");
            hostAndPortSet.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
        }

        jedisCluster = new JedisCluster(hostAndPortSet, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);

    }

    public DefaultJedisCluster(String clusterAddr) {

        this.connectionTimeout = 10000;
        this.soTimeout = 10000;

        int maxAttempts = 3;
        int minIdle = 8;
        int maxIdle = 8;
        int maxTotal = 1000;

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        //最大连接数
        poolConfig.setMaxTotal(maxTotal);

        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        String addr = "";
        String[] hostPort = null;
        String[] addrArr = clusterAddr.split(",");
        for(int i = 0; i < addrArr.length; i ++) {
            addr = addrArr[i];
            hostPort = addr.split(":");
            hostAndPortSet.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
        }

        jedisCluster = new JedisCluster(hostAndPortSet);
    }

    public DefaultJedisCluster(String clusterAddr, String password) {

        this.connectionTimeout = 10000;
        this.soTimeout = 10000;

        int maxAttempts = 3;
        int minIdle = 8;
        int maxIdle = 8;
        int maxTotal = 1000;

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        //最大连接数
        poolConfig.setMaxTotal(maxTotal);

        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        String addr = "";
        String[] hostPort = null;
        String[] addrArr = clusterAddr.split(",");
        for(int i = 0; i < addrArr.length; i ++) {
            addr = addrArr[i];
            hostPort = addr.split(":");
            hostAndPortSet.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
        }

        jedisCluster = new JedisCluster(hostAndPortSet, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);

    }

    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
