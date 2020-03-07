package com.zhihao.config;

import com.zhihao.service.config.SpringAndMybatisConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 代替web.xml 继承核心前端控制器
 */
public class MyInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringAndMybatisConfig.class};  //加载spring容器
//        return new Class[]{SpringMVCConfig.class};
    }
 
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVCConfig.class}; //加载spring-mvc容器
    }
 
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; //拦截所有
    }
}