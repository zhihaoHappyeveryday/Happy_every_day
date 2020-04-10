package com.zhihao.order.service.service.impl;

import com.eshuix.common.orderpay.enums.PayType;
import com.eshuix.common.orderpay.handlersuccess.HandlerAfterSuccess;
import com.eshuix.idm.order.service.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 14:46
 * @Description: 会员充值成功后业务处理
 * @Versions 1.0
 **/
@Service("memberHandlerAfterSuccess")
public class MemberHandlerAfterSuccessImpl implements HandlerAfterSuccess {

    @Autowired
    private TestService testService;

    @Override
    public boolean successHandler(String outTradeNo, String thirdTradeNo, PayType payType) {
        if (testService.add() > 0){
            System.out.println("会员充值成功");
            //返回处理成功
            return true;
        }
        return false;
    }
}
