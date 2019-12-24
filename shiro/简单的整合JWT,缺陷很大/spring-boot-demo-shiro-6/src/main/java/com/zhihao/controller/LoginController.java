package com.zhihao.controller;

import com.zhihao.entity.User;
import com.zhihao.service.UserService;
import com.zhihao.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2019/12/12 13:58
 * @Description: 用户登录
 * @Versions 1.0
 **/
@RestController
public class LoginController {

    private Map<String,Object> resultMap;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletResponse response;

    /**
     * 登录免认证  登录成功签发token
     *
     * @param username 用户名
     * @param password 密码
     * @return java.util.Map 简陋的结果包装
     * @author: zhihao
     * @date: 2019/12/12
     * {@link #}
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        resultMap = new LinkedHashMap<>();
        // 密码加密
        String md5 = new SimpleHash("MD5", password, username, 1024).toString();
        User user = userService.findUserByName(username);
        if(user != null && md5.equals(user.getPassword())){
            resultMap.put("code", "success");
            resultMap.put("token", jwtUtil.createJWT(user.getId(), user.getUsername()));
            return resultMap;
        }
        resultMap.put("code","error");
        resultMap.put("msg","用户不存在或者密码错误");
        return resultMap;
    }

    /**
     * 登录成功跳转到首页
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
     * 退出(注销)访问根目录解析视图到登录页面
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
