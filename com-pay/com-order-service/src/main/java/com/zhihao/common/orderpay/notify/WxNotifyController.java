package com.zhihao.common.orderpay.notify;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.zhihao.common.orderpay.config.WXPayConfig;
import com.zhihao.common.orderpay.enums.PayType;
import com.zhihao.common.orderpay.handlersuccess.HandlerAfterSuccess;
import com.zhihao.common.orderpay.util.PayUtil;
import com.zhihao.common.orderpay.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 14:18
 * @Description:
 * @Versions 1.0
 **/
@RestController
@RequestMapping("/notify/wx")
public class WxNotifyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WXPayConfig wxPayConfig;

    /**
     * 支付宝支付状态结果异步通知
     *
     * @return
     */
    @PostMapping(value = "/")
    public String WxNotify(HttpServletRequest request) throws Exception {
        //将异步通知中所有参数都经过工具类处理后存放到Map中
        String xmlData = null;
        try {
            xmlData = PayUtil.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.info("微信公众号支付异步通知>>数据无法转成xml异常！");
            e.printStackTrace();
            return PayUtil.generatePayErrorReplyXML();
        }
        // 将得到xml转为map,验证签名是否成功
        Map<String, String> xmlToMapData = WXPayUtil.xmlToMap(xmlData);
        //获取签名sign
        String sign = xmlToMapData.get("sign");
        //重新生成新sign  注意加密方式要和下单一样
        String newsign = WXPayUtil.generateSignature(xmlToMapData, wxPayConfig.getPublicKey(), WXPayConstants.SignType.MD5);
        // 验证签名验证是否成功
        if (!sign.equals(newsign)) {
            log.info("微信App支付异步通知>>验证签名失败！");
            return PayUtil.generatePayErrorReplyXML();
        }
        //查询return_code是否是支付成功了success
        if (xmlToMapData.get("return_code").equals("SUCCESS")) {
            // 1.商户订单号
            String out_trade_no = xmlToMapData.get("out_trade_no");
            // 2.微信支付订单号
            String thirdTradeNo = xmlToMapData.get("transaction_id");
            // 3.获取applicationContextBeanName
            try {
                String BeanName = xmlToMapData.get("attach");
                //4.根据bean名称获取HandlerAfterSuccess实现类
                HandlerAfterSuccess handler = SpringContextHolder.getBean(BeanName);
                if (handler.successHandler(out_trade_no, thirdTradeNo, PayType.WX_APP)) {
                    //5.处理成功返回成功码给支付宝
                    return PayUtil.generatePaySuccessReplyParams();
                }
            } catch (NoSuchBeanDefinitionException e) {
                log.error("获取Bean失败,原因:{}", e.getMessage());
                e.printStackTrace();
            }
            //返回成功状态码给微信接口
            log.info("微信App支付,订单支付成功! 订单号:{}", out_trade_no);
            //返回成功状态码给微信端
            return PayUtil.generatePaySuccessReplyXML();
        }
        log.info("微信App支付异步通知->支付结果返回的不是成功的状态码，请详查！-> 订单号: " + xmlToMapData.get("out_trade_no"));
        return PayUtil.generatePayErrorReplyXML();
    }
}
