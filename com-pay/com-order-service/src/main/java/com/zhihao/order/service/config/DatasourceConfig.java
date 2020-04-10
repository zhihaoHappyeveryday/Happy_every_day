package com.zhihao.order.service.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZhiHao
 * @Date: 2019/10/22
 * @Description: druid数据源配置
 */
@Configuration
public class DatasourceConfig {

    /**
     * 告诉springboot使用druid数据源
     *
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}