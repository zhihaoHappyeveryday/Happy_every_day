package com.zhihao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zhihao
 * @Date: 7/3/2020 下午 10:43
 * @Description: 实现WebMvcConfigurer配置类
 * @Versions 1.0
 **/
@Configuration
@EnableWebMvc //启动spring-mvc功能
@ComponentScan(basePackages = {"com.zhihao.controller"}) //包扫描)
//@Import(value = {SpringAndMybatisConfig.class}) //导入spring配置类
public class SpringMVCConfig implements WebMvcConfigurer {

    /**
     * 配置视图解析器
     *
     * @return org.springframework.web.servlet.view.InternalResourceViewResolver
     * @author: zhihao
     * @date: 7/3/2020
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/",".jsp");
    }
}
