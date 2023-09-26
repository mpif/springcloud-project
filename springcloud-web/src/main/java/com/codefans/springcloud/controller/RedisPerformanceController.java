package com.codefans.springcloud.controller;

import com.codefans.springcloud.redis.DefaultJedisCluster;
import com.codefans.springcloud.redis.JedisSingleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: codefans
 * @Date: 2022-09-18 12:06
 */
//@RestController
//@RequestMapping("/redis/performance")
public class RedisPerformanceController {

    private Logger log = LoggerFactory.getLogger(RedisPerformanceController.class);

    private JedisSingleClient jedisSingleClient;
    private JedisPool jedisPool;

    private DefaultJedisCluster jedisCluster;

    private AtomicLong readConcurrency = new AtomicLong(0);
    private long maxRead = 0;
    private AtomicLong writeConcurrency = new AtomicLong(0);
    private long maxWrite = 0;
    private AtomicLong clusterReadConcurrency = new AtomicLong(0);
    private long maxClusterRead = 0;
    private AtomicLong clusterWriteConcurrency = new AtomicLong(0);
    private long maxClusterWrite = 0;

    private Random random = new Random();

    public RedisPerformanceController() {
        String host = "192.168.131.166";
        int port = 8001;
        int connectionTimeout = 3000;
        int soTimeout = 3000;
        jedisSingleClient = new JedisSingleClient(host, port, connectionTimeout, soTimeout);
        jedisPool = jedisSingleClient.createJedisPool();

        String clusterAddr = "192.168.131.166:7001,192.168.131.166:7002,192.168.131.166:7003";
        jedisCluster = new DefaultJedisCluster(clusterAddr);

    }

    @GetMapping
    public String read(HttpServletRequest request) {
        readConcurrency.incrementAndGet();
        String key = "singleRedisPormanceTestKey_";
        Jedis jedis = jedisPool.getResource();
        String getVal = jedis.get(key + random.nextInt(1696));
        jedis.close();
        maxRead = readConcurrency.decrementAndGet() > maxRead ? readConcurrency.get() : maxRead;
        log.info("单机并发数测试, 当前并发数为:{}, 最大并发数:{}, 本次读取到的值为:{}", readConcurrency.get(), maxRead, getVal);
        return "单机并发数为:" + readConcurrency.get();
    }

    @GetMapping(params = "cluster=read")
    public String clusterRead(HttpServletRequest request) {
        clusterReadConcurrency.incrementAndGet();
        String key = "firstKey";
        String getVal = jedisCluster.get(key+"_" + random.nextInt(1696));
        maxClusterRead = clusterReadConcurrency.decrementAndGet() > maxClusterRead ? clusterReadConcurrency.get() : maxClusterRead;
        log.info("集群读性能测试, 当前并发数为:{}, 最大并发数:{}, 本次读取到的值为:{}", clusterReadConcurrency.get(), maxClusterRead, getVal);
        return "集群并发数为:" + clusterReadConcurrency.get();
    }

    @PostMapping
    public String write(HttpServletRequest request) {
        writeConcurrency.incrementAndGet();
        String key = "setWithNXAndEXAndExpiredTime";
        String value = "setWithNXAndEXAndExpiredTime_value";
        Jedis jedis = jedisPool.getResource();
        String returnObj = jedis.set(key, value);
        jedis.close();
        maxWrite = writeConcurrency.decrementAndGet() > maxWrite ? writeConcurrency.get() : maxWrite;
        log.info("单机写性能测试, 当前并发数为:{}, 最大并发数:{}", writeConcurrency.get(), maxWrite);
        return "单机写性能测试, 当前并发数为:" + writeConcurrency.get();
    }

    @PostMapping(params = "cluster=write")
    public String clusterWrite(HttpServletRequest request) {
        clusterWriteConcurrency.incrementAndGet();
        String key = "setWithNXAndEXAndExpiredTime";
        String value = "setWithNXAndEXAndExpiredTime_value";
        String returnObj = jedisCluster.set(key, value);
        maxClusterWrite = clusterWriteConcurrency.decrementAndGet() > maxClusterWrite ? clusterWriteConcurrency.get() : maxClusterWrite;
        log.info("集群写性能测试, 当前并发数为:{}, 最大并发数:{}", clusterWriteConcurrency.get(), maxClusterWrite);
        return "集群写性能测试, 当前并发数为:" + clusterWriteConcurrency.get();
    }
}
