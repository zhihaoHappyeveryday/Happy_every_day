package com.zhihao.service.impl;

import com.zhihao.dao.UserRoleMapper;
import com.zhihao.entity.Role;
import com.zhihao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhihao
 * @Date: 2019/12/13 16:19
 * @Description:
 * @Versions 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<Role> findRoleByUserName(String username) {
        return userRoleMapper.findRoleByUserName(username);
    }
}
