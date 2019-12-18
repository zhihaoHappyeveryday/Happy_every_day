package com.zhihao.service.impl;

import com.zhihao.dao.UserMapper;
import com.zhihao.entity.User;
import com.zhihao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 13:53
 * @Description: 用户
 * @Versions 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }




}
