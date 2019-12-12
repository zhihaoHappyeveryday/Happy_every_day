package com.zhihao.controller;

import com.zhihao.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 13:58
 * @Description: 用户登录
 * @Versions 1.0
 **/
@RestController
public class UserController {

    private Map<String,Object> resultMap = new ConcurrentHashMap();

    /**
     * 登录认证
     *
     * @param username 用户名
     * @param password 密码
     * @return java.util.Map 简陋的结果包装
     * @author: zhihao
     * @date: 2019/12/12
     * {@link #}
     */
    @PostMapping("/login")
    public Map login( String username, String password){
        // 密码md5加密 (省略)

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        try {
            //没有抛出异常,说明登录成功
            subject.login(token);
            resultMap.put("code", "success");
        } catch (UnknownAccountException e){
            //抛弃自定义认证失败的异常信息返回
            resultMap.put("msg", e.getMessage());
        }catch (IncorrectCredentialsException e) {
            resultMap.put("msg", e.getMessage());
        }catch (LockedAccountException e) {
            resultMap.put("msg", e.getMessage());
        }catch (AuthenticationException e) {
            resultMap.put("msg", "认证失败");
        }
        return resultMap;
    }

    /**
     * 登录成功解析视图跳转到首页
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author: zhihao
     * @date: 2019/12/12
     * {@link #}
     */
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User){
            view.addObject("user", principal);
        }
        view.setViewName("index");
        return view;
    }

    /** 
     * 访问login解析视图到登录页面
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author: zhihao
     * @date: 2019/12/12 
     * {@link #}
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;
    }

    /** 
     * 访问根目录解析视图到登录页面
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author: zhihao
     * @date: 2019/12/12 
     * {@link #}
     */
    @GetMapping("/")
    public ModelAndView logins() {
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;
    }
}
