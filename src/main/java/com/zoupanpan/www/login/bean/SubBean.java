package com.zoupanpan.www.login.bean;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

/**
 * @author zoupanpan
 * @version 2020/5/17 20:18
 */
public class SubBean {

    @NotEmpty(message = "sub登陆名不能为空")
    @Range(max = 10, min = 1, message = "sub登陆名1到10字符之间")
    private String userName;

    public SubBean(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
