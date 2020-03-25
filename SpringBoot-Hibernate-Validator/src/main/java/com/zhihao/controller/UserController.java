package com.zhihao.controller;

import com.zhihao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhihao
 * @Date: 2020/3/25 16:09
 * @Description:
 * @Versions 1.0
 **/
@RestController
@Validated //加上开启验证器
public class UserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/getUser")
    public User getUser(@NotNull(message = "名字不能为空") String name,
                        @NotNull(message = "年龄不能为空")@Min(value = 1L,message = "年龄最小1") Integer age, @NotNull(message = "性别不能为空") String sex){
        log.info("成功访问到了,参数是{},{},{}",name,age,sex);
        return new User(name, age, sex);
    }

    @GetMapping("/getUsers")
    public User getUsers(@Valid User user){
        log.info("成功访问到了,参数是{}",user);
        return user;
    }
}
