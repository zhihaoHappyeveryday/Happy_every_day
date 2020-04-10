package com.zhihao.common.orderpay.initclient;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.zhihao.common.orderpay.config.AliPayConfig;
import com.zhihao.common.orderpay.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/4/9 18:35
 * @Description: 初始化支付宝支付客户端
 * @Versions 1.0
 **/
@Component
public class DefaultAliPayClient {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static AliPayConfig aliPayConfig;

    //实例化客户端
    private static AlipayClient alipayClient;

    /** 销售产品码 */
    private final static String productCode = "QUICK_MSECURITY_PAY";

    /**
     *  支付宝App方式下单
     *
     * @param applicationContextBeanName
     * @param outTradeNo
     * @param totalAmount
     * @param subject
     * @param body
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/4/10
     */
    public Map<String, String> aliPayPlaceAnOrder(String outTradeNo, String totalAmount,
                                                   String subject, String body,String applicationContextBeanName) throws Exception {
        if (alipayClient == null){
            //为空才实例化客户端
            alipayClient = initClient();
        }
        Map<String, String> resultMap = new HashMap<>();
        AlipayTradeWapPayModel Model = new AlipayTradeWapPayModel();
        //商户订单号，需要保证不重复
        Model.setOutTradeNo(outTradeNo);
        //订单金额
        Model.setTotalAmount(totalAmount);
        //交易主题
        Model.setSubject(subject);
        //商品名称  对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
        Model.setBody(body);
        //销售产品码，商家和支付宝签约的产品码，该产品请填写固定值：QUICK_MSECURITY_PAY
        Model.setProductCode(productCode);
        //设置支付宝交易超时 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
        Model.setTimeoutExpress(aliPayConfig.getTimeoutExpress());
        //设置applicationContextBeanName 上下文bean名称, 异步获取上下文bean处理成功业务
        //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行 UrlEncode 之后才可以发送给支付宝
        Model.setPassbackParams(URLEncoder.encode(applicationContextBeanName, "UTF-8"));
        //创建阿里请求对象 (和扫码创建的对象不同这里是App)
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        //业务请求参数的集合
        alipayTradeAppPayRequest.setBizModel(Model);
        //设置后台异步通知的地址，在手机端支付后支付宝会通知后台(成功或者失败)，手机端的真实支付结果依赖于此地址 支付宝服务器主动通知商户服务器里指定的页面 http/https 路径。建议商户使用 https
        alipayTradeAppPayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());
        //这里和普通的接口调用不同，使用的是AppPay
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
        log.info("支付宝App支付下单成功,单号为:{}", outTradeNo);
        //封装调起支付窗口的body结果返回
        resultMap.put("payData", response.getBody());
        return resultMap;
    }


    /**
     * 验证支付宝参数合法性
     * 使用支付宝SDK自带的验证方法进行验证 AlipaySignature.rsaCheckV1
     *
     * @param params 请求参数转换后的Map
     * @return boolean
     * @author: zhihao
     * @date: 2020/4/10
     */
    public boolean checkedParams(Map<String, String> params){
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            return AlipaySignature.rsaCheckV1(params, aliPayConfig.getPublicKey(),"UTF-8" , "RSA2");
        } catch (AlipayApiException e) {
            log.error("---支付宝API异常>>");
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 实例化客户端,alipayClient 只需要初始化一次，
     * 后续调用不同的API都可以使 用同一个 alipayClient 对象。
     *
     * @return com.alipay.api.AlipayClient
     * @author: zhihao
     * @date: 2020/4/10
     */
    private AlipayClient initClient() {
        //ApplicationContext 应用上下文获取框架bean
        aliPayConfig = SpringContextHolder.getBean(AliPayConfig.class);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getRequestAddr(), aliPayConfig.getId(),
                aliPayConfig.getPrivateKey(), "json",
                "UTF-8", aliPayConfig.getPublicKey(), "RSA2");
        return alipayClient;
    }
}
