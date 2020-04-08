package com.zhihao.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author: zhihao
 * @Date: 2020/4/8 14:56
 * @Description:
 * @Versions 1.0
 **/
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        //使用单节点模式
        config.useSingleServer()
                //可以用"rediss://"来启用SSL连接
                .setAddress("redis://localhost:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
