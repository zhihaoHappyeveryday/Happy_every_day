package com.zhihao.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 13:58
 * @Description: 用户登录
 * @Versions 1.0
 **/
@RestController
public class UserController {
    /**
     * 模拟从数据库获取了全部用户
     *
     * @return java.lang.String
     * @author: zhihao
     * @date: 2019/12/13
     */
    @RequiresPermissions(value = "user:user") //使用shiro权限注解标明,只能拥有这个user:user权限的用户访问
    @RequestMapping("/list")
    public String getUserList(){
        String hashAlgorithmName = "MD5";//加密方式
        String password = "321";//密码原值
        String salt = "love";//盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,password,salt,hashIterations);
        System.out.println(result.toString());
        String userList ="拥有获取全部用户的权限 ``user:user``";
        return userList;
    }

    @RequiresPermissions(value = "user:add")
    @RequestMapping("/add")
    public String addUser(){
        String userList ="拥有添加用户权限 ``user:add``";
        return userList;
    }

    @RequiresPermissions(value = "user:delete")
    @RequestMapping("/delete")
    public String deleteUser(){
        String userList ="拥有删除用户权限 ``user:delete``";
        return userList;
    }
}
