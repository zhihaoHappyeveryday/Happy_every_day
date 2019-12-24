package com.zhihao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2019/12/13 15:22
 * @Description: 权限实体
 * @Versions 1.0
 **/
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = -66L;
    private Integer id;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;
}
