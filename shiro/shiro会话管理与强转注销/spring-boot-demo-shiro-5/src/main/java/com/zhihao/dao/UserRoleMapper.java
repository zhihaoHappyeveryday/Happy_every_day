package com.zhihao.dao;

import com.zhihao.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
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
