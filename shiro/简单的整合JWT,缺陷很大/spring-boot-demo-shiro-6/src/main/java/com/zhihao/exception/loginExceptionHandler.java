package com.zhihao.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2019/12/24 12:57
 * @Description: 登录相关异常处理
 * @Versions 1.0
 **/
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class loginExceptionHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Map exceptionMap;


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map exception(Exception e){
        exceptionMap = new LinkedHashMap();
        exceptionMap.put("msg", e.getMessage());
        log.error(e.getMessage());
        return exceptionMap;
    }
    /**
     * 拦截权限不足异常,并响应
     *
     * @param e
     * @return java.util.Map
     * @author: zhihao
     * @date: 2019/12/24
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public Map unauthorizedException(UnauthorizedException e){
        exceptionMap = new LinkedHashMap();
        exceptionMap.put("msg", e.getMessage());
        log.error(e.getMessage());
        return exceptionMap;
    }
}
