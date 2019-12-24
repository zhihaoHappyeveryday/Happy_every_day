package com.zhihao.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhihao
 * @Date: 2019/12/17 18:22
 * @Description: shiro会话监听器
 * @Versions 1.0
 **/
public class ShiroSessionListener implements SessionListener {

    /**
     * 维护着个原子类型的Integer对象，用于统计在线Session的数量
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.getAndIncrement();
//        System.out.println("登录+1=="+sessionCount.get());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("登录-1=="+sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("过期-1=="+sessionCount.get());
    }
}
