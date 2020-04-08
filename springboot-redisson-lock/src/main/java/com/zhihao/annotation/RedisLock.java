package com.zhihao.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {

    /** 锁的key */
    String key();

    /** 锁的过期秒数,默认是10秒 */
    int expire() default 10;

    /** 尝试加锁，最多等待时间, 默认10秒 */
    long waitTime() default 10L;

    /** 锁的超时,时间单位 默认秒 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
