package com.zoupanpan.www.base.log;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zoupanpan
 * @version 2020/5/31 19:34
 */
public class LogPointcut implements Pointcut {

    private static final AnnotationClassFilter annotationClassFilter = new AnnotationClassFilter(Controller.class);
    private static final AnnotationMethodMatcher annotationMethodMatcher = new AnnotationMethodMatcher(RequestMapping.class);

    @Override
    public ClassFilter getClassFilter() {
        return annotationClassFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return annotationMethodMatcher;
    }
}
