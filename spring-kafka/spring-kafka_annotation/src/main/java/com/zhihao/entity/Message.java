package com.zhihao.entity;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2020/3/24 15:48
 * @Description:
 * @Versions 1.0
 **/
public class Message implements Serializable {

    private static final long serialVersionUID = 6678420965611108427L;

    private String message;

    private String comeFrom;

    public Message() {
    }

    public Message(String message, String comeFrom) {
        this.message = message;
        this.comeFrom = comeFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", comeFrom='" + comeFrom + '\'' +
                '}';
    }
}
