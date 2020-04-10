package com.zhihao.order.service.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Swagger2 配置属性
 * </p>
 * @description: Swagger2 配置类
 * @author: zhihao
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {
    
	/**
     * 创建RestApi 并包扫描controller
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .paths(PathSelectors.any())
            	 //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .build();
    }
    
	/**
     * 创建Swagger页面 信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("订单微服")
                .description("订单微服")
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}