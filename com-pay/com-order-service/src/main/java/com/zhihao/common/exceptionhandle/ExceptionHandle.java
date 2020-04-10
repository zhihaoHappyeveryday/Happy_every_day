package com.zhihao.common.exceptionhandle;

import com.zhihao.common.base.BaseController;
import com.zhihao.common.base.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @Author: zhihao
 * @Date: 2020/4/2 17:14
 * @Description: 全局异常处理
 * @Versions 1.0
 **/
@RestControllerAdvice
public class ExceptionHandle extends BaseController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    /**
     * 最大异常处理
     *
     * @param e 异常
     * @return  BaseController
     * @author: zhihao
     * @date: 2020/4/2
     */
    @ExceptionHandler(value = Exception.class)
    //返回500响应码
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData exceptionHandler(Exception e) {
        log.error("错误信息:{} , 请求路径:{}", e.getMessage(), request.getRequestURI());
        return retResultErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), request);
    }


    /**
     * 处理普通参数注解校验异常
     *
     * @param e
     * @return  ResultData
     * @author: zhihao
     * @date: 2020/4/3
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    //返回400响应码
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResultData validationHandler(ConstraintViolationException e) {
        log.error("错误信息:{} , 请求路径:{}", e.getMessage(), request.getRequestURI());
        StringBuilder builder = new StringBuilder();
        e.getConstraintViolations().stream()
                .forEach((violation) -> {
                    builder.append(violation.getMessage()).append(",");
                });
        //去掉最后一个 ','
        builder.deleteCharAt(builder.length() - 1);
        return retResultErrorCode(HttpStatus.BAD_REQUEST.value(), builder.toString(), request);
    }

    /**
     * 处理实体类@Valid注解校验的异常
     *
     * @param e
     * @return  ResultData
     * @author: zhihao
     * @date: 2020/4/3
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResultData bindExceptionResult(BindException e) {
        log.error("错误信息:{} , 请求路径:{}", e.getMessage(), request.getRequestURI());
        StringBuilder builder = new StringBuilder();
        e.getFieldErrors().stream().forEach(fieldError -> {
            builder.append(fieldError.getField())
                    .append(fieldError.getDefaultMessage()).append(",");
        });
        //去掉最后一个 ','
        builder.deleteCharAt(builder.length() - 1);
        return retResultErrorCode(HttpStatus.BAD_REQUEST.value(), builder.toString(), request);
    }
}
