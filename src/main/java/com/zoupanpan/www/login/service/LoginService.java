package com.zoupanpan.www.login.service;

import com.zoupanpan.www.base.bean.ResultBean;
import com.zoupanpan.www.login.bean.LoginParamBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:23
 */
@Service
public class LoginService {



    public static ResultBean login(LoginParamBean loginParamBean){


        try{
            UsernamePasswordToken token = new UsernamePasswordToken(loginParamBean.getUserName(),loginParamBean.getPassword());
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            currentUser.getSession().setTimeout(1000*60*60);
            System.out.println(currentUser.getSession().getId());
        }catch(Exception e){
            return ResultBean.FAILURE;
        }

        return ResultBean.SUCCESS;

    }

}
