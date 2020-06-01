package com.zoupanpan.www.base.log;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * @author zoupanpan
 * @version 2020/5/31 20:08
 */
public class LogMethodInterceptor implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LogMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        //获取目标类
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        //获取指定方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        //获取真正执行的方法,可能存在桥接方法
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        //获取方法上注解
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod, RequestMapping.class);
        if (requestMapping == null) {
            requestMapping = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod.getDeclaringClass(), RequestMapping.class);
        }
        Object[] arguments = invocation.getArguments();


        logger.info("requestPath:{}", requestMapping.path());

        return invocation.proceed();
    }
}
