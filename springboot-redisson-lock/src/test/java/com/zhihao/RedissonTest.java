package com.zhihao;

import com.zhihao.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: zhihao
 * @Date: 2020/4/8 15:19
 * @Description:
 * @Versions 1.0
 **/
@SpringBootTest
@RunWith(value = SpringRunner.class)
@Slf4j
public class RedissonTest {

    private Integer count = 1000;

    @Autowired
    private RedissonClient redissonClient;

    private ExecutorService pool = Executors.newFixedThreadPool(100);

    @Test
    public void Test() throws InterruptedException {
//        IntStream.range(0, 1000).forEach(i -> pool.execute(() -> this.noLock()));
        // 测试类中使用AOP需要手动代理
        RedissonTest target = new RedissonTest();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LockMethodAspect aspect = new LockMethodAspect(redissonClient);
        factory.addAspect(aspect);
        RedissonTest proxy = factory.getProxy();
        IntStream.range(0, 1000).forEach(i -> pool.execute(() -> proxy.youLock()));
        TimeUnit.SECONDS.sleep(30);
    }

    /**
     * 使用加锁,数字输出正常了
     */
    @RedisLock(key = "youLock")
    public void youLock() {
        count--;
        log.info("integer为{}", count);
    }

    /**
     * 没有使用到线程安全的输出了重复的数字,或者没有顺序
     */
    public void noLock() {
        count--;
        log.info("integer为{}", count);
    }
}
