package com.zhihao;

import cn.hutool.core.util.StrUtil;
import com.zhihao.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: zhihao
 * @Date: 2020/4/8 16:51
 * @Description:
 * @Versions 1.0
 **/
@Aspect
@Component
public class LockMethodAspect {

    private Logger log = LoggerFactory.getLogger(getClass());

    private RedissonClient redisson;

    @Autowired
    public LockMethodAspect(RedissonClient redisson) {
        this.redisson = redisson;
    }

    /** 
     * 切入点
     *
     * @author: zhihao
     * @date: 2020/4/8 
     */
    @Pointcut("@annotation(com.zhihao.annotation.RedisLock)")
    public void onLock() {

    }

    /** 
     * 环绕通知
     *
     * @param point
     * @return java.lang.Object 
     * @author: zhihao
     * @date: 2020/4/8 
     */
    @Around("onLock()")
    public Object around(ProceedingJoinPoint point) throws InterruptedException {
        //1.获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Object[] args = point.getArgs();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        //获取锁的key
        String key = redisLock.key();
        if (StrUtil.isBlank(key)) {
            throw new RuntimeException("分布式锁键不能为空");
        }
        //获取锁
        RLock lock = redisson.getLock(key);
        //尝试加锁
        boolean tryLock = lock.tryLock(redisLock.waitTime(), redisLock.expire(), redisLock.timeUnit());
        if (tryLock){
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                log.info("执行出现问题:{}", throwable.getMessage());
                throwable.printStackTrace();
            }finally {
                //释放锁
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
        return null;
    }
}
