package com.zhihao.common.orderpay.notify;

import com.zhihao.common.orderpay.enums.PayType;
import com.zhihao.common.orderpay.handlersuccess.HandlerAfterSuccess;
import com.zhihao.common.orderpay.initclient.DefaultAliPayClient;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 11:56
 * @Description: 阿里支付宝异步通知
 * @Versions 1.0
 **/
@RestController
@RequestMapping("/notify/ali")
public class AliNotifyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DefaultAliPayClient aliPayClient;

    /**
     * 支付宝支付状态结果异步通知
     *
     * @return
     */
    @PostMapping(value = "/")
    public String AliNotify(HttpServletRequest request) {
        //将异步通知中所有参数都经过工具类处理后存放到Map中
        Map<String, String> params = PayUtil.parseParams(request.getParameterMap());
        // 验证通知合法性
        boolean verify_result = aliPayClient.checkedParams(params);
        // 验证异步通知信息参数
        if (!verify_result) {
            log.info("支付宝App异步通知->签名验证失败");
            return PayUtil.generatePayErrorReplyParams();
        }
        //查看状态是否是交易成功状态
        if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
            //1.获取订单号
            String out_trade_no = params.get("out_trade_no");
            //2.支付宝支付订单号
            String thirdTradeNo = params.get("trade_no");
            //3.获取applicationContextBeanName
            try {
                String BeanName = URLDecoder.decode(params.get("passback_params"), "UTF-8");
                //4.根据bean名称获取HandlerAfterSuccess实现类
                HandlerAfterSuccess handler = SpringContextHolder.getBean(BeanName);
                if (handler.successHandler(out_trade_no,thirdTradeNo, PayType.ZFB_APP)) {
                    //5.处理成功返回成功码给支付宝
                    return PayUtil.generatePaySuccessReplyParams();
                }
            } catch (UnsupportedEncodingException e) {
                log.error("公共参数BeanName解析失败,原因:{}", e.getMessage());
                e.printStackTrace();
            } catch (NoSuchBeanDefinitionException e) {
                log.error("获取Bean失败,原因:{}", e.getMessage());
                e.printStackTrace();
            }
        }
        log.info("支付宝App异步通知->支付结果返回的不是成功的状态码，请详查订单号:{}",params.get("out_trade_no"));
        return PayUtil.generatePayErrorReplyParams();
    }
}
