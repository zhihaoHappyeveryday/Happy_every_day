package com.zhihao.service;

import com.zhihao.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * 根据用户名查询用户拥有所有角色
     *
     * @param username
     * @return java.util.List<com.zhihao.entity.Role>
     * @author: zhihao
     * @date: 2019/12/13
     */
    List<Role> findRoleByUserName(String username);
}
