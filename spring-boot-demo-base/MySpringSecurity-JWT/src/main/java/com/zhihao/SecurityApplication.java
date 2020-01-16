package com.zhihao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zhihao
 * @Date: 15/1/2020 下午 9:23
 * @Description: 启动类
 * @Versions 1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.zhihao.dao"})
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class,args);
    }
}
