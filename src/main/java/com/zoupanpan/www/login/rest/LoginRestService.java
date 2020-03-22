package com.zoupanpan.www.login.rest;

import com.zoupanpan.www.base.bean.ResultBean;
import com.zoupanpan.www.login.bean.LoginParamBean;
import com.zoupanpan.www.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:20
 */
@Controller
@RequestMapping("/login")
public class LoginRestService {

    @Autowired
    private LoginService loginService;

    @PutMapping
    @ResponseBody
    public ResultBean login(@RequestBody LoginParamBean loginParamBean){
        return LoginService.login(loginParamBean);

    }
}
