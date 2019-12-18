package com.zhihao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2019/12/13 15:20
 * @Description: 角色实体
 * @Versions 1.0
 **/
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -66L;
    private Integer id;

    private String name;

    private String memo;
}
