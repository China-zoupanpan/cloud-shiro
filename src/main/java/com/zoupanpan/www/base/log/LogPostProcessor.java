package com.zoupanpan.www.base.log;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zoupanpan
 * @version 2020/5/31 23:20
 */
@Configuration
public class LogPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        this.advisor = new LogAopAdvisor();
    }
}
