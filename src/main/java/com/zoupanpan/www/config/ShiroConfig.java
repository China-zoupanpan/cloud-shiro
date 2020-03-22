package com.zoupanpan.www.config;

import com.google.common.collect.Lists;
import com.zoupanpan.www.login.auth.LoginRealm;
import com.zoupanpan.www.login.auth.ShiroHandlerFilter;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zoupanpan
 * @version 2020/3/22 16:28
 */
@Configuration
public class ShiroConfig {




//    @Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher =  new HashedCredentialsMatcher();
//        //指定加密方式为MD5
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(1024);
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }

    @Bean("loginRealm")
    public LoginRealm userRealm() {

//        userRealm.setCredentialsMatcher(matcher);
        return new LoginRealm();
    }

    /**
     * 注入 securityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("loginRealm") Realm realm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm.
        securityManager.setRealm(realm);
//        securityManager.setSessionManager(new DefaultSessionManager());
        //ThreadContext.bind(securityManager);

//        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


    @Bean
    public FilterRegistrationBean addFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ShiroHandlerFilter shiroFilter = new ShiroHandlerFilter();
        shiroFilter.setSecurityManager(securityManager);
        filterRegistrationBean.setFilter(shiroFilter);
        filterRegistrationBean.setUrlPatterns(Lists.newArrayList("*"));
        return filterRegistrationBean;
    }




}
