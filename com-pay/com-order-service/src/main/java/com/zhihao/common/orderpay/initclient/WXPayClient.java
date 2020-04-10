package com.zhihao.common.orderpay.initclient;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.zhihao.common.orderpay.config.WXPayConfig;
import com.zhihao.common.orderpay.util.HttpsUtils;
import com.zhihao.common.orderpay.util.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 12:53
 * @Description: 初始化支付宝支付客户端
 * @Versions 1.0
 **/
@Component
public class WXPayClient {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WXPayConfig wxPayConfig;

    /**
     * 微信App方式下单
     *
     * @param applicationContextBeanName
     * @param outTradeNo
     * @param totalAmount
     * @param body
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/4/10
     */
    public Map<String, String> wXPayPlaceAnOrder(String outTradeNo,String applicationContextBeanName,
                                                 String totalAmount, String body) throws Exception {
        //创建线程安全的Map
        Map<String, String> resultDate = new ConcurrentHashMap<>();
        //封装下单请求参数键值对
        Map<String, String> reqData = new ConcurrentHashMap<>();
        //应用ID 必填
        reqData.put("appid", wxPayConfig.getId());
        //商户号 必填
        reqData.put("mch_id", wxPayConfig.getMchId());
        //随机字符串 必填-每次请求都要是新的随机字符串WXPayUtil.generateNonceStr()
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述 必填
        reqData.put("body", body);
        //商品订单号 必填
        reqData.put("out_trade_no", outTradeNo);
        //支付总金额 (将元转换为分) 必填  使用自己工具类进行转换
        reqData.put("total_fee", PayUtil.changeY2F(totalAmount));
        //设置applicationContextBeanName 上下文bean名称, 异步获取上下文bean处理成功业务
        //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。微信会在异步通知时将该参数原样返回
        reqData.put("attach", applicationContextBeanName);
        //终端IP 不知道怎么写可以写本地127.0.0.1 必填
        reqData.put("spbill_create_ip", "127.0.0.1");//客户端主机
        //异步回调通知地址通知url必须为外网可访问的url，不能携带参数   必填
        reqData.put("notify_url", wxPayConfig.getNotifyUrl());
        //交易类型JSAPI 必填
        reqData.put("trade_type", "APP");
        //将Map转为带签名xml格式进行请求
        String signedXml = WXPayUtil.generateSignedXml(reqData, wxPayConfig.getPublicKey(), WXPayConstants.SignType.MD5);
        //微信下单
        String resultString = HttpsUtils.sendPayPostRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", signedXml);
        //将xml格式串转换为Map获取结果
        Map<String, String> responseparams = WXPayUtil.xmlToMap(resultString);
        //判断下单成功进行下面的操作
        if ("SUCCESS".equals(responseparams.get("return_code"))) {
            //应用ID:  成功响应的返回的参数有appid (必填)
            resultDate.put("appid", responseparams.get("appid"));
            //商户号: 成功响应的返回的参数有商户号mch_id (必填)
            resultDate.put("partnerid", responseparams.get("mch_id"));
            //预支付交易会话ID (必填)
            resultDate.put("prepayid", responseparams.get("prepay_id"));
            //扩展字段 (必填)
            resultDate.put("package", "Sign=WXPay");
            //随机字符串 (必填)
            resultDate.put("nonceStr", responseparams.get("nonce_str"));
            //时间戳 (必填)
            resultDate.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestampMs()));
            //注：这里采用 MD5进行签名,因为预下单支付那里采用MD5,要保持一致
            String Sign = WXPayUtil.generateSignature(resultDate,wxPayConfig.getPublicKey(), WXPayConstants.SignType.MD5);
            //签名 (必填)
            resultDate.put("sign", Sign);
            log.info("微信App支付下单成功,单号: {}",outTradeNo);
            return resultDate;
        }
        log.error("微信App支付下单失败,原因:{}",responseparams);
        //抛出下单失败
        throw new RuntimeException("微信App支付下单失败,请求响应的状态码不是SUCCESS");
    }
}
