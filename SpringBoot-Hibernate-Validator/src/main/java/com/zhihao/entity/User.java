package com.zhihao.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2020/3/25 16:07
 * @Description:
 * @Versions 1.0
 **/
public class User implements Serializable {

    @NotNull(message = "名字不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    private Integer age;

    @NotNull(message = "性别不能为空")
    private String sex;

    @NotNull(message = "生日不能为空")
    private String birthday;

    public User() {
    }

    public User(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
