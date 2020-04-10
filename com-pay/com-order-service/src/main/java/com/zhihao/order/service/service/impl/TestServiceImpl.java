package com.zhihao.order.service.service.impl;

import com.eshuix.idm.order.service.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 14:48
 * @Description:
 * @Versions 1.0
 **/
@Service
public class TestServiceImpl implements TestService {

    @Override
    public int add(){
        System.out.println("成功插入数据库");
        return 1;
    }
}
