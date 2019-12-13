package com.zhihao.service.impl;

import com.zhihao.dao.UserPermissionMapper;
import com.zhihao.entity.Permission;
import com.zhihao.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhihao
 * @Date: 2019/12/13 16:20
 * @Description: 获取用户所有权限
 * @Versions 1.0
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserPermissionMapper userPermissionMapper;
    @Override
    public List<Permission> findPermissionByUserName(String username) {
        return userPermissionMapper.findPermissionByUserName(username);
    }
}
