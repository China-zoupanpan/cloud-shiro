package com.zoupanpan.www.config;

import com.google.common.collect.Lists;
import com.zoupanpan.www.login.auth.LoginRealm;
import com.zoupanpan.www.login.auth.ShiroHandlerFilter;
import com.zoupanpan.www.login.shiro.RedisSessionDao;
import com.zoupanpan.www.login.shiro.RedisWebSecurityManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author zoupanpan
 * @version 2020/3/22 16:28
 */
@Configuration
public class ShiroConfig {

    @Bean("realm")
    public Realm userRealm() {
        return new LoginRealm();
    }

    @Bean("redisSessionDao")
    public RedisSessionDao getRedisSessionDao(@Autowired RedisConnectionFactory redisConnectionFactory) {
        return new RedisSessionDao(redisConnectionFactory);
    }

    @Bean("redisSecurityManager")
    public RedisWebSecurityManager getRedisWebSecurityManager(@Autowired Realm realm,
                                                              @Autowired RedisSessionDao redisSessionDao) {
        return new RedisWebSecurityManager(realm, redisSessionDao);
    }

//    @Bean("jdbcSessionDao")
//    public JDBCSessionDAO getJDBCSessionDAO(@Autowired SessionEntityDao sessionEntityDao) {
//        return new JDBCSessionDAO(sessionEntityDao);
//    }
//
//
//    @Bean("jdbcSecurityManager")
//    public JDBCWebSecurityManager getJDBCWebSecurityManager(@Autowired Realm realm,
//                                                            @Autowired JDBCSessionDAO jdbcSessionDao) {
//        return new JDBCWebSecurityManager(realm, jdbcSessionDao);
//    }


    @Bean
    public FilterRegistrationBean addFilter(@Qualifier("redisSecurityManager") WebSecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ShiroHandlerFilter shiroFilter = new ShiroHandlerFilter();
        shiroFilter.setSecurityManager(securityManager);
        filterRegistrationBean.setFilter(shiroFilter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setUrlPatterns(Lists.newArrayList("*"));
        return filterRegistrationBean;
    }



}
