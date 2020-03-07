package com.zhihao.dao;

import com.zhihao.entity.User;

import java.util.List;
public interface UserMapper {

    /**
     *  分页查询全部
     *
     * @return java.util.List<com.zhihao.entity.User>
     * @author: zhihao
     * @date: 7/3/2020
     */
    List<User> findAll();
}
