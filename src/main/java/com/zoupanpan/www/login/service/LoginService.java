package com.zoupanpan.www.login.service;

import com.zoupanpan.www.base.bean.ResultBean;
import com.zoupanpan.www.login.bean.LoginParamBean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:23
 */
@Service
@Primary
@Role(2)
public class LoginService {


    //有效期6个小时
    private static final int EXPIRED_SIX_HOUR = 60 * 60 * 1000;

    @Transactional(readOnly = true)
    public ResultBean login(LoginParamBean loginParamBean) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginParamBean.getUserName(), loginParamBean.getPassword());
            token.setRememberMe(false);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            loginParamBean.setToken(Objects.toString(currentUser.getSession().getId(), null));
            currentUser.getSession().setTimeout(EXPIRED_SIX_HOUR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResultBean.FAILURE(10000, e.getMessage());
        }

        return ResultBean.SUCCESS(loginParamBean);

    }

}
