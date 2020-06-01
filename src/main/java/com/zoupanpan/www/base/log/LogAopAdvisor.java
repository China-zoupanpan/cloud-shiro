package com.zoupanpan.www.base.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author zoupanpan
 * @version 2020/5/31 18:49
 */
public class LogAopAdvisor extends AbstractPointcutAdvisor {
    private Pointcut pointcut = new LogPointcut();
    private Advice advice = new LogMethodInterceptor();

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
