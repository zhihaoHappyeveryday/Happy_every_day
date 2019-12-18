package com.zhihao.service;

import com.zhihao.entity.User;

/**
 * 用户接口
 */
public interface UserService {


    User findUserByName(String name);

}
