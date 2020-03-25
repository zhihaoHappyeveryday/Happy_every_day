package com.zhihao.entity;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2020/3/25 16:59
 * @Description: 错误结果
 * @Versions 1.0
 **/
public class ExceptionResult implements Serializable {

    private String message;

    private Integer code;

    public ExceptionResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
