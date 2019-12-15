package com.zhihao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 10:04
 * @Description: 启动类
 * @Versions 1.0
 **/
@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
    }

    /**
     * 异常解析器
     *
     * @return org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    @Bean
    public SimpleMappingExceptionResolver  simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        //拦截到AuthorizationException 就跳转到resources资源下的 如果带文件夹需要加上文件夹: /xxx/403模板页面
        properties.setProperty("AuthorizationException", "/403");
        resolver.setExceptionMappings(properties);
        return resolver;
    }
}
