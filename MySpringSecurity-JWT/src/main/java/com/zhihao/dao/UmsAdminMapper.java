package com.zhihao.dao;

import com.zhihao.entity.UmsAdmin;
import com.zhihao.entity.UmsPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UmsAdminMapper {

    UmsAdmin getAdminByUsername(String username);

    List<UmsPermission> getPermissionList(Long id);
}
