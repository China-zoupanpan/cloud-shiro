package com.zoupanpan.www.login.bean;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:55
 */
public class LoginParamBean {

    private String userName;
    private String password;
    private String sercet;
    private String ticket;
    private String token;

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

    public String getSercet() {
        return sercet;
    }

    public void setSercet(String sercet) {
        this.sercet = sercet;
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
}
