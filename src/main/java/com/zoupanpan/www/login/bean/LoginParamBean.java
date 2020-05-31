package com.zoupanpan.www.login.bean;

import com.zoupanpan.www.base.ValidatorUtils;
import com.zoupanpan.www.util.PassWord;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:55
 */
public class LoginParamBean {

    @NotEmpty(message = "登陆名不能为空", groups = ValidatorUtils.AddGroup.class)
    @Range(max = 10, min = 1, message = "登陆名1到10字符之间", groups = {ValidatorUtils.AddGroup.class})
    private String userName;
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "密码不能含有汉字")
    @PassWord
    private String password;
    @NotNull(message = "11254")
    private String secret;
    @Null(message = "ticket 必须为null")
    private String ticket;
    //    @Length(min = 2, max = 20, message = "token 2到20",
//            groups = {ValidatorUtils.AddGroup.class, ValidatorUtils.UpdateGroup.class})
    @Size(min = 2, max = 20, message = "token 2到20",
            groups = {ValidatorUtils.AddGroup.class, ValidatorUtils.UpdateGroup.class})
    private String token;
    @DecimalMax(value = "157885545000", groups = ValidatorUtils.AddGroup.class)
    @DecimalMin(value = "-157885545000", message = "validateNum 最小值lll")
    private Integer validateNum;
    @Max(value = 100, message = "11255")
    @Min(value = 5, message = "11256")
    @NotNull(message = "11257")
    private Integer validateNum1;
    @Digits(integer = 3, fraction = 2, message = "小数位数最大未2")
    @Max(value = 100, message = "整数部分最大3")
    @Null(message = "validateNum2 必须为null")
    private Double validateNum2;

    @Digits(integer = 3, fraction = 2, message = "小数位数最大未2")
    @Range(max = 10, min = 1, message = "validateNum3 1到10之间", groups = {ValidatorUtils.AddGroup.class})
    private Integer validateNum3;

    @NotNull(message = "emailList 不能为空", groups = {ValidatorUtils.AddGroup.class})
    @Size(max = 3, min = 1, message = "emailList在3和1之间")
//    @Email.List(value = {@Email(regexp = "^.*@163.com$", message = "163邮箱"),
//            @Email(regexp = "^.*@qq.com$", message = "qq邮箱")})
    private List<@Length(min = 2, max = 20, message = "email2到20") String> emailList;

    private List<@Valid SubBean> subBeanList;
    //    @Email.List(value = {@Email(regexp = "^163com$", message = "163邮箱"),
//            @Email(regexp = "^qqcom$", message = "qq邮箱")})
    @Email(regexp = "^www\\.pp\\.com.*$", message = "qq邮箱")
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getValidateNum() {
        return validateNum;
    }

    public void setValidateNum(Integer validateNum) {
        this.validateNum = validateNum;
    }

    public Integer getValidateNum1() {
        return validateNum1;
    }

    public void setValidateNum1(Integer validateNum1) {
        this.validateNum1 = validateNum1;
    }

    public Double getValidateNum2() {
        return validateNum2;
    }

    public void setValidateNum2(Double validateNum2) {
        this.validateNum2 = validateNum2;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getValidateNum3() {
        return validateNum3;
    }

    public void setValidateNum3(Integer validateNum3) {
        this.validateNum3 = validateNum3;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SubBean> getSubBeanList() {
        return subBeanList;
    }

    public void setSubBeanList(List<SubBean> subBeanList) {
        this.subBeanList = subBeanList;
    }
}
