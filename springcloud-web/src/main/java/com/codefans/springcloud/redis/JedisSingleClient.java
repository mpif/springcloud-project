package com.codefans.springcloud.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * @author: codefans
 * @date: 2018-06-22 09:45
 * redis单机模式
 */
public class JedisSingleClient extends JedisClientBase {

    Jedis jedis;
    JedisPool jedisPool;

    public JedisSingleClient(Jedis jedis) {
        this.jedis = jedis;
    }

    public JedisSingleClient(String host, int port) {
        this.host = host;
        this.port = port;
        jedis = new Jedis(host, port);
    }

    public JedisSingleClient(String host, int port, int connectionTimeout, int soTimeout) {
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        jedis = new Jedis(host, port, connectionTimeout, soTimeout);
    }

    /**
     * 创建jedis池
     * @return
     */
    public JedisPool createJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(3000);
        jedisPoolConfig.setMaxIdle(5000);
        jedisPoolConfig.setMaxTotal(10000);
        jedisPool = new JedisPool(jedisPoolConfig, host, port);
        return jedisPool;
    }

    public JedisSingleClient(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
        jedis = new Jedis(host, port);
        jedis.auth(this.password);
//        jedis.connect();
    }

    public void setAuth(String password) {
        jedis.auth(password);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    public boolean setnx(String key, String value) {
        return jedis.setnx(key, value) == 1;
    }

    /**
     *
     * @param key
     * @param value
     * @param nxxx, NX-如果不存在则设置; XX-如果已存在则设置
     * @param expx, EX-单位秒;PX-单位毫秒
     * @param expiredTime, 过期时间
     * @return
     */
    public boolean set(String key, String value, String nxxx, String expx, int expiredTime) {
        SetParams setParams = new SetParams();
        if("NX".equals(nxxx)) {
            setParams = setParams.nx();
        } else {
            setParams = setParams.xx();
        }
        if("EX".equals(expx)) {
            setParams = setParams.ex(expiredTime);
        } else {
            setParams = setParams.px(expiredTime);
        }

        return "OK".equals(jedis.set(key, value, setParams));
    }

    public String getString(String key) {
        return jedis.get(key);
    }

    /**
     * 返回老的value,设置新的value
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        return jedis.getSet(key, value);
    }

    /**
     * 返回删除的记录数
     * @param key
     * @return
     */
    public long delete(String key) {
        return jedis.del(key);
    }

    /**
     * 返回删除的记录数
     * @param key
     * @param value
     * @return
     */
    public long delete(String key, String value) {
        String oldValue = jedis.get(key);
        if(oldValue != null && oldValue.equals(value)) {
            return jedis.del(key);
        }
        return 0;
    }

    /**
     * 返回删除的记录数
     * @param key
     * @param value
     * @param luaStript
     * @return
     */
    public Object deleteUsingEvalLua(String key, String value, String luaStript) {
        return jedis.eval(luaStript, Collections.singletonList(key), Collections.singletonList(value));
    }

}
