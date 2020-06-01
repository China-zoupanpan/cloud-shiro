package com.zoupanpan.www.base.webmvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zoupanpan
 * @version 2020/5/31 15:37
 */
public class LangInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String lang = request.getParameter("lang");
        if (StringUtils.isBlank(lang)) {

            lang = request.getHeader("lang");
        }

        if (StringUtils.isBlank(lang)) {
            lang = "zh";
        }
        LangHelper.INSTANCE.setLang(lang);
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        LangHelper.INSTANCE.clear();
        //清除
        System.out.println(LangHelper.INSTANCE.getLang());
    }

}
