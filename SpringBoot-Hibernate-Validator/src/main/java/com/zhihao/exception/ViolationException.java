package com.zhihao.exception;

import com.zhihao.entity.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @Author: zhihao
 * @Date: 2020/3/25 17:01
 * @Description: ViolationException异常处理
 * @Versions 1.0
 **/
@RestControllerAdvice
public class ViolationException {


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResult exceptionResult(ConstraintViolationException exception){
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String[] split = violation.getPropertyPath().toString().split("\\.");
            builder.append(split[1])
                    .append(violation.getMessage()).append(",");
        }
        return new ExceptionResult(builder.deleteCharAt(builder.length()-1).toString(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResult bindExceptionResult(BindException exception){
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : exception.getFieldErrors()) {
            builder.append(fieldError.getField())
                    .append(fieldError.getDefaultMessage()).append(",");
        }
        return new ExceptionResult(builder.deleteCharAt(builder.length()-1).toString(), HttpStatus.BAD_REQUEST.value());
    }

    /*@ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView ResultErrorHtml(ConstraintViolationException exception,
                                        HttpServletResponse response){
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String[] split = violation.getPropertyPath().toString().split("\\.");
            builder.append(split[1])
                    .append(violation.getMessage()).append(",");
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("error");
        view.addObject("message", builder.deleteCharAt(builder.length()-1).toString());
        view.addObject("code", HttpStatus.BAD_REQUEST.value());
        return view;
    }*/
}
