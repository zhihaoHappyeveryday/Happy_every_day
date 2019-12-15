package com.zhihao.dao;

import com.zhihao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserByName(String name);
}
