package util;

import com.codefans.springcloud.common.util.JsonUtil;
import org.junit.jupiter.api.Test;

/**
 * @Author: codefans
 * @Date: 2022-08-05 15:51
 */

public class JsonUtilTest {


    @Test
    public void minifyTest() {

        String jsonStr = "{\"msgtype\": \"text\",\"text\": {\"content\": \"今天 天气 晴！\",\"mentioned_list\":[\"@all\"]}}";
        System.out.println(JsonUtil.minify(jsonStr));

    }
}
