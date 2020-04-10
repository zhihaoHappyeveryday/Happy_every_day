package com.zhihao.common.orderpay.handlersuccess;

import com.zhihao.common.orderpay.enums.PayType;

/**
 * 异步通知成功后的处理规范
 * <p>
 * 每个功能的成功需要处理的业务逻辑都实现此接口,说明会有多个实现类。
 * 如需其他地方注入使用,使用@resource,或者@Autowired搭配@Qualifier根据名称注入。
 * 每实现一个功能实现类,都需要去HandlerSuccessBeanName增加对应关系。
 * </p>
 * {@link com.zhihao.common.orderpay.enums.HandlerSuccessBeanName}
 * @author: zhihao
 * @date: 2020/4/10
 */
public interface HandlerAfterSuccess {

    /**
     * 成功业务处理
     *
     * @param outTradeNo   商户订单号
     * @param thirdTradeNo 第三方交易订单号
     * @param payType      支付类型
     * @return boolean
     * @author: zhihao
     * @date: 2020/4/10
     */
    boolean successHandler(String outTradeNo, String thirdTradeNo, PayType payType);
}
