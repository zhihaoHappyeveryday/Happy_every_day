package com.zhihao.shiro;

import com.zhihao.entity.Permission;
import com.zhihao.entity.Role;
import com.zhihao.entity.User;
import com.zhihao.service.PermissionService;
import com.zhihao.service.RoleService;
import com.zhihao.service.UserService;
import com.zhihao.util.JWTToken;
import com.zhihao.util.JwtUtil;
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

    /**
     *  支持自定义认证令牌
     *
     * @param token
     * @return boolean
     * @author: zhihao
     * @date: 2019/12/24
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 授权模块>>>>获取用户角色和权限
     *
     * @param principal
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //通过jwt工具解析token获取登录后的获取到用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        //创建授权对象进行封装角色和权限信息进去进行返回 注意不是SimpleAuthenticationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户角色集
        List<Role> roleList = roleService.findRoleByUserName(username);
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getMemo());
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
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的
        String token = (String) authenticationToken.getCredentials();
        String username = null;
        try {
            username = jwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            //抛出token认证失败
            throw new AuthenticationException("token认证失败");
        }
        // 通过用户名到数据库查询用户信息
        User user = userService.findUserByName(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }
}
