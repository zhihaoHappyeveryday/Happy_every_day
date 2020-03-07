package com.zhihao.service;

import com.github.pagehelper.PageInfo;
import com.zhihao.entity.User;

public interface UserService {
    PageInfo<User> findAll();
}
