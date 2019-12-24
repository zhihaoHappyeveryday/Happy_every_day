package com.zhihao.service;

import com.zhihao.entity.Permission;

import java.util.List;

public interface PermissionService {

    /**
     *  根据用户名 查询出该用户拥有的所有权限
     *
     * @param username 用户名
     * @return java.util.List<com.zhihao.entity.Permission>
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    List<Permission> findPermissionByUserName(String username);
}
