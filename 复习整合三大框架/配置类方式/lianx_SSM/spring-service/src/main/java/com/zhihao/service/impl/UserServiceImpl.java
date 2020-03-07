package com.zhihao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhihao.dao.UserMapper;
import com.zhihao.entity.User;
import com.zhihao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zhihao
 * @Date: 7/3/2020 下午 7:05
 * @Description:
 * @Versions 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public PageInfo<User> findAll() {
        //使用分页
        PageHelper.startPage(1, 10);
        List<User> all = userMapper.findAll();
        return new PageInfo<>(all);
    }
}
