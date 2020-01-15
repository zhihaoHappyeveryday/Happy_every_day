package com.zhihao.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhihao
 * @Date: 15/1/2020 下午 8:17
 * @Description: 用户信息
 * @Versions 1.0
 **/
public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;

    private List<UmsPermission> permissionList;

    public AdminUserDetails(UmsAdmin umsAdmin,List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = this.permissionList.stream()
                //返回不等于null的权限值
                .filter(umsPermission -> umsPermission.getValue() != null)
                //将UmsPermission类型集合转换成SimpleGrantedAuthority类型集合
                .map(umsPermission -> new SimpleGrantedAuthority(umsPermission.getValue()))
                .collect(Collectors.toList());
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return this.umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 账号是否启用
     *
     * @return boolean
     * @author: zhihao
     */
    @Override
    public boolean isEnabled() {
        return this.umsAdmin.getStatus().equals("1");
    }
}
