package com.zoupanpan.www.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author zoupanpan
 * @version 2020/3/22 12:54
 */
@Configuration
public class SpringFactory implements ApplicationContextAware {

    private static  ApplicationContext applicationContext;

    public static <T> T  getBean(String beanId){
        return (T) applicationContext.getBean(beanId);
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringFactory.applicationContext = applicationContext;
    }

}
