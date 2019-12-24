package com.zhihao.dao;

import com.zhihao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findUserByName(String name);

    int lockNumber(@Param("id") String id, @Param("status")String status);

    List<User> findLockNumberAll();
}
