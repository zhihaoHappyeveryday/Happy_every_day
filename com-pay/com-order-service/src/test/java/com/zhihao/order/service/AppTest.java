package ocm.zhihao.order.service;

import com.zhihao.common.orderpay.UnifiedPlaceAnOrder;
import com.zhihao.common.orderpay.enums.PayType;
import com.zhihao.common.orderpay.util.SpringContextHolder;
import com.zhihao.order.service.OrderServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class AppTest {

    @Test
    public void test1() throws Exception {
        String str = "unifiedPlaceAnOrder";
        UnifiedPlaceAnOrder bean = SpringContextHolder.getBean(str);
        Map<String, String> stringStringMap = bean.placeAnOrder(PayType.ZFB_APP, "null", "null",
                "null", "null", "null");
        System.out.println(stringStringMap);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        String aliPayConfig = URLEncoder.encode("aliPayConfig", "UTF-8");
        System.out.println(aliPayConfig);
        String decode = URLDecoder.decode(aliPayConfig, "UTF-8");
        System.out.println(decode);
    }


}



