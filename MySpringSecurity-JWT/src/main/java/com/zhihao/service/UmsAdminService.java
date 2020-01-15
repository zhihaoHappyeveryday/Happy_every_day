package com.zhihao.service;

import com.zhihao.entity.UmsAdmin;
import com.zhihao.entity.UmsPermission;

import java.util.List;

public interface UmsAdminService {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return com.zhihao.entity.UmsAdmin
     * @author: zhihao
     * @date: 15/1/2020
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 根据用户id获取用户拥有的权限
     *
     * @param id 用户id
     * @return java.util.List<com.zhihao.entity.UmsPermission>
     * @author: zhihao
     * @date: 15/1/2020
     */
    List<UmsPermission> getPermissionList(Long id);

    /**
     * 登录成功返回token
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);

    /**
     * 刷新token
     * @param token
     * @return
     */
    String refreshToken(String token);
}
