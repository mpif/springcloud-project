import com.codefans.springcloud.redis.DefaultJedisCluster;
import com.codefans.springcloud.redis.JedisSingleClient;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

/**
 * @Author: codefans
 * @Date: 2022-09-19 0:39
 */

public class JedisSingleClientTest {

    @Test
    public void singleTest() {

        String jedisArr = "192.168.131.131";
        int port = 8001;
        JedisSingleClient singleJedis = new JedisSingleClient(jedisArr, port);
        String key = "singleRedisPormanceTestKey_";
        Random random = new Random();
        for(int i = 0; i < 100; i ++) {
            String getVal = singleJedis.get(key + random.nextInt(1696));
            System.out.println("key:" + key + ", val:" + getVal);
        }

    }

    @Test
    public void singleRedisMultiThreadTest() {

        String jedisArr = "192.168.131.131";
        int port = 8001;
        int connectionTimeout = 3000;
        int soTimeout = 3000;
        JedisSingleClient singleJedis = new JedisSingleClient(jedisArr, port, connectionTimeout, soTimeout);
        String key = "singleRedisPormanceTestKey_";
        Random random = new Random();
//        for(int i = 0; i < 100; i ++) {
//            String getVal = singleJedis.get(key + random.nextInt(1696));
//            System.out.println("key:" + key + ", val:" + getVal);
//        }

        JedisPool jedisPool = new JedisPool(jedisArr, port);
        for(int i = 0; i < 20; i ++) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Jedis jedis = jedisPool.getResource();
                            String getVal = jedis.get(key + random.nextInt(1696));
                            System.out.println("key:" + key + ", val:" + getVal);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
