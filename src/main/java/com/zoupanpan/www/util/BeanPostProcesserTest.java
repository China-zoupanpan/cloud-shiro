package com.zoupanpan.www.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zoupanpan
 * @version 2020/4/12 0:19
 */
@Component
public class BeanPostProcesserTest implements BeanPostProcessor, CustomizeBean {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("beanName" + beanName);
        return bean;
    }


}
