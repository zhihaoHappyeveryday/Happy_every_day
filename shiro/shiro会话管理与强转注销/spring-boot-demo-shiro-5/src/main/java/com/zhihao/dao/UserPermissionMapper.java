package com.zhihao.dao;

import com.zhihao.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPermissionMapper {
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
