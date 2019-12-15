package com.zhihao.shiro;

import com.zhihao.entity.Permission;
import com.zhihao.entity.Role;
import com.zhihao.entity.User;
import com.zhihao.service.PermissionService;
import com.zhihao.service.RoleService;
import com.zhihao.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取用户角色和权限
     *
     * @param principal
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //通过shiro工具获取登录后的user对象,获取到用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        //创建授权对象进行封装角色和权限信息进去进行返回 注意不是SimpleAuthenticationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户角色集
        List<Role> roleList = roleService.findRoleByUserName(username);
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getName());
        }
        System.out.println("用户拥有的角色>>>"+roleSet);
        //添加角色进角色授权
        info.setRoles(roleSet);
        List<Permission> permissionList = permissionService.findPermissionByUserName(username);
        Set<String> permissionSet = new HashSet<>();
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getName());
        }
        System.out.println("用户拥有的权限>>>"+permissionSet);
        //添加权限进权限授权
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    	// 获取用户输入的用户名和密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        
//        System.out.println("用户" + username + "认证-----ShiroRealm.doGetAuthenticationInfo");

        // 通过用户名到数据库查询用户信息
        User user = userService.findUserByName(username);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
