package com.zhihao.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhihao"})
@EnableEurekaClient
public class OrderServiceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceApplication.class);
	
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        logger.warn("order-service started successfully");
    }

}

