package com.zhihao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zhihao
 * @Date: 2020/4/8 14:34
 * @Description:
 * @Versions 1.0
 **/
@SpringBootApplication
public class RedissonLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedissonLockApplication.class,args);
    }
}
