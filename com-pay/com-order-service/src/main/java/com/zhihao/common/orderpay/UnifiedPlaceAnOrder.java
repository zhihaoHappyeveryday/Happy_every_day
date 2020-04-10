package com.zhihao.common.orderpay;

import com.eshuix.common.orderpay.enums.PayType;
import com.eshuix.common.orderpay.initclient.DefaultAliPayClient;
import com.eshuix.common.orderpay.initclient.WXPayClient;
import com.zhihao.common.orderpay.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/4/9 17:24
 * @Description: 支付与微信的统一下单
 * @Versions 1.0
 **/
@Component
public class UnifiedPlaceAnOrder  {

    @Autowired
    private DefaultAliPayClient aliPayClient;

    @Autowired
    private WXPayClient wxPayClient;
    /**
     * 支付-统一下单
     *
     * @param payType 支付类型
     * @param applicationContextBeanName 异步通知成功状态后,业务逻辑处理实现类的bean名称
     * @param outTradeNo 订单号
     * @param totalAmount 金额
     * @param subject 商品主题
     * @param body 商品描述
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/4/9
     */
    public Map<String,String> placeAnOrder(PayType payType, String outTradeNo, String totalAmount,
                                           String subject, String body,String applicationContextBeanName) throws Exception {
        //校验下单参数
        if (StringUtils.isAnyBlank(outTradeNo,applicationContextBeanName,totalAmount,subject,body)){
            //抛出异常给全局异常处理
            throw new RuntimeException("下单参数不能为空");
        }
        Map<String,String> result = null;
        switch(payType) {
            case ZFB_APP:
                result = aliPayClient.aliPayPlaceAnOrder(outTradeNo,totalAmount,subject,body,applicationContextBeanName);
                break;
            case WX_APP:
                result = wxPayClient.wXPayPlaceAnOrder(outTradeNo,applicationContextBeanName,totalAmount,body);
                break;
        }
        return result;
    }
}
