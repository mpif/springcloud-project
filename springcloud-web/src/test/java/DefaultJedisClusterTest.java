import com.codefans.springcloud.redis.DefaultJedisCluster;
import org.junit.Test;

import java.util.Random;

/**
 * @Author: codefans
 * @Date: 2022-09-18 22:54
 */

public class DefaultJedisClusterTest {

    @Test
    public void clusterTest() {

        String clusterAddr = "192.168.131.131:7001,192.168.131.131:7002,192.168.131.131:7003";
        DefaultJedisCluster jedisCluster = new DefaultJedisCluster(clusterAddr);
        String key = "firstKey";
        Random random = new Random();
        String getVal = jedisCluster.get(key+"_" + random.nextInt(1696));
        System.out.println("key:" + key + ", val:" + getVal);

    }

}
