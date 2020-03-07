package com.zhihao.entity;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 7/3/2020 下午 6:18
 * @Description: user
 * @Versions 1.0
 **/
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
