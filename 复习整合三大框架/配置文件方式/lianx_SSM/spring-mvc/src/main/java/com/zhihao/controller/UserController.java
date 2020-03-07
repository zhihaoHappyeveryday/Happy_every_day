package com.zhihao.controller;

import com.github.pagehelper.PageInfo;
import com.zhihao.entity.User;
import com.zhihao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhihao
 * @Date: 7/3/2020 下午 9:02
 * @Description: user控制层
 * @Versions 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public String findAll(){
        PageInfo<User> all = userService.findAll();
        System.out.println(all);
        return "index";
    }
}
