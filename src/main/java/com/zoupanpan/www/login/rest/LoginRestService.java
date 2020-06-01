package com.zoupanpan.www.login.rest;

import com.zoupanpan.plugin.kafka.Producer;
import com.zoupanpan.www.base.ValidatorUtils;
import com.zoupanpan.www.base.bean.ResultBean;
import com.zoupanpan.www.base.webmvc.LangHelper;
import com.zoupanpan.www.base.webmvc.SimpleException;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private Producer producer;

//    @Lookup
//    protected HttpServletRequest getRequest() {
//        return null;
//    }

    @Autowired
    protected HttpServletRequest request;

//
//    @Lookup
//    protected HttpServletResponse getResponse() {
//        return null;
//    }


    @PutMapping
    @ResponseBody
    public ResultBean login(@RequestBody LoginParamBean loginParamBean, @CookieValue("token") String token) {
        ResultBean validate = ValidatorUtils.validate(loginParamBean, ValidatorUtils.AddGroup.class);
        if (validate.getCode() != 0) {
            return validate;
        }
        producer.sendMessage("login");
        request.getRequestURI();
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

    @RequestMapping(path = "/hello",
            //headers = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json;charset=GBK",
            method = {RequestMethod.GET, RequestMethod.PUT}
    )
    @ResponseBody
    public ResultBean test() {
        System.out.println("hello world");

        int i = 1 / 0;
        return ResultBean.FAILURE(1001, "失败");

    }

    @RequestMapping(path = "/hello2",
            //headers = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json;charset=GBK",
            method = {RequestMethod.GET, RequestMethod.PUT}
    )
    @ResponseBody
    public ResultBean hello() {
        System.out.println("hello world");
        System.out.println(LangHelper.INSTANCE.getLang());
        try {
            //int i = 1 / 0;
        } catch (Exception e) {
            throw new SimpleException(e.getMessage(), 100);
        }

        return ResultBean.FAILURE(1001, "失败");

    }
}
