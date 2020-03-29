package com.zoupanpan.www.login.rest;

import com.zoupanpan.www.base.bean.ResultBean;
import com.zoupanpan.www.login.bean.LoginParamBean;
import com.zoupanpan.www.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:20
 */
@Controller
@RequestMapping("/login")
public class LoginRestService {

    @Autowired
    private LoginService loginService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping
    @ResponseBody
    public ResultBean login(@RequestBody LoginParamBean loginParamBean){
        return loginService.login(loginParamBean);

    }


    @GetMapping(path = "/test",
            //headers = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json;charset=GBK"
    )
    @RequiresAuthentication
    @ResponseBody
    public ResultBean test(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Session session1 = ((ShiroHttpSession) request.getSession()).getSession();

        return ResultBean.FAILURE(1001, "失败");

    }
}
