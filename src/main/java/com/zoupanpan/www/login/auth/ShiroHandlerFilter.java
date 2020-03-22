package com.zoupanpan.www.login.auth;

import org.apache.shiro.web.servlet.AbstractShiroFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zoupanpan
 * @version 2020/3/22 19:21
 */

public class ShiroHandlerFilter  extends AbstractShiroFilter {

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(servletRequest, servletResponse, chain);
        HttpServletRequest request=(HttpServletRequest)servletRequest;


    }
}
