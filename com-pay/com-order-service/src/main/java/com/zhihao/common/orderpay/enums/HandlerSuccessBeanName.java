package com.zhihao.common.orderpay.enums;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 11:56
 * @Description: 功能成功后业务逻辑实现类Bean名称
 * <p>
 *  此枚举对应的是支付功能异步通知成功支付状态后的业务功能处理,
 *  例如: 咖啡需要App支付功能, 先把异步通知支付成功后需要处理的业务功能逻辑,根据HandlerAfterSuccess规范
 *      接口进行实现,并需要指定实现类注册Bean的名称,并增加到此枚举。
 * </p>
 * @Versions 1.0
 **/
public enum HandlerSuccessBeanName {

    /**{@link com.zhihao.order.service.service.impl.CoffeeHandlerAfterSuccessImpl} */
    COFFEE_SUCCESS_HANDLER("咖啡异步通知支付成功状态码后的业务处理实现类","coffeeHandlerAfterSuccess"),

    /**{@link com.zhihao.order.service.service.impl.MemberHandlerAfterSuccessImpl} */
    MEMBER_SUCCESS_HANDLER("会员异步通知支付成功状态码后的业务处理实现类","memberHandlerAfterSuccess");

    private String description;

    private String beanName;

    HandlerSuccessBeanName(String description, String beanName) {
        this.description = description;
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
