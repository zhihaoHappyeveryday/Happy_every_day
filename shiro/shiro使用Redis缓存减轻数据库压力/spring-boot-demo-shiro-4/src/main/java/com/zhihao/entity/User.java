package com.zhihao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 10:48
 * @Description: 用户实体
 * @Versions 1.0
 **/
@Data
public class User implements Serializable {

    private String id;

    private String username;

    private String password;

    private String status;

    private Date createTime;
}
