package com.zoupanpan.www.login.auth;

import com.google.common.collect.Sets;
import com.zoupanpan.www.login.shiro.ShiroConstant;
import com.zoupanpan.www.util.SpringFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zoupanpan
 * @version 2020/3/22 19:21
 */

public class ShiroHandlerFilter extends AbstractShiroFilter {

    private static final Logger log = LoggerFactory.getLogger(ShiroHandlerFilter.class);

    private RequestMappingHandlerMapping handlerMapping;

    private transient boolean initHandlerMappingFlag = true;

    private PermissionAnnotationHandler permissionAnnotationHandler = new PermissionAnnotationHandler();

    private RoleAnnotationHandler roleAnnotationHandler = new RoleAnnotationHandler();

    private AuthenticatedAnnotationHandler authenticatedAnnotationHandler = new AuthenticatedAnnotationHandler();


    private ConcurrentHashMap<String, Set<Annotation>> annotationsMap = new ConcurrentHashMap<>();


    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        Throwable t = null;

        try {
            //包装request，从写getSession方法
            final ServletRequest request = prepareServletRequest(servletRequest, servletResponse, chain);
            final ServletResponse response = prepareServletResponse(request, servletResponse, chain);
            String ticket = getTicket((HttpServletRequest) request);
            final Subject subject = createSubject(ticket, request, response);
            //请求中带的ticket，系统中不存在
            if (StringUtils.isNotBlank(ticket) && !Objects.equals(ticket, subject.getSession().getId())) {
                log.info("ticket错误,ticket:{}==sessionId:{}", ticket, subject.getSession(false).getId());
                subject.getSession(false).stop();
            }
            ThreadContext.bind(subject);
            try {
                Set<Annotation> unionAll = getAnnotationSet((HttpServletRequest) request);
                if (CollectionUtils.isNotEmpty(unionAll)) {
                    checkAuthentication((HttpServletRequest) request);
                    subject.getSession().touch();
                }
            } catch (Exception e) {
                if (response instanceof HttpServletResponse) {
                    SimpleCookie simpleCookie = new SimpleCookie(ShiroConstant.LOGIN_TICKET_NAME);
                    simpleCookie.setMaxAge(0);
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    simpleCookie.saveTo((HttpServletRequest) request, httpResponse);
                }
                throw e;
            }
            executeChain(request, response, chain);
        } catch (ExecutionException ex) {
            t = ex.getCause();
        } catch (Throwable throwable) {
            t = throwable;
        }

        if (t != null) {
            if (t instanceof ServletException) {
                throw (ServletException) t;
            }
            if (t instanceof IOException) {
                throw (IOException) t;
            }
            //otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
            String msg = "Filtered request failed.";
            throw new ServletException(msg, t);
        }
    }

    private String getTicket(HttpServletRequest httpRequest) {
        String ticket = httpRequest.getParameter(ShiroConstant.LOGIN_TICKET_NAME_PARAMETER);
        if (StringUtils.isNotBlank(ticket)) {
            return ticket;
        }
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (Objects.equals(name, ShiroConstant.LOGIN_TICKET_NAME)) {
                return cookie.getValue();
            }
        }
        return httpRequest.getHeader(ShiroConstant.LOGIN_TICKET_NAME);
    }

    protected WebSubject createSubject(String ticket, ServletRequest request, ServletResponse response) {
        WebSubject.Builder builder = new WebSubject.Builder(request, response);
        builder.sessionId(ticket);
        return builder.buildWebSubject();
    }


    private boolean checkAuthentication(HttpServletRequest request) {
        Set<Annotation> unionAll = getAnnotationSet(request);
        for (Annotation annotation : unionAll) {
            if (annotation instanceof RequiresAuthentication) {
                authenticatedAnnotationHandler.assertAuthorized(annotation);
            }
            if (annotation instanceof RequiresRoles) {
                roleAnnotationHandler.assertAuthorized(annotation);
            }
            if (annotation instanceof RequiresPermissions) {
                permissionAnnotationHandler.assertAuthorized(annotation);
            }
        }

        return true;
    }

    private Set<Annotation> getAnnotationSet(HttpServletRequest request) {
        return annotationsMap.computeIfAbsent(request.getMethod() + request.getRequestURI(), (url) -> addAndInitAnnotationsMap(request));

    }

    private Set<Annotation> addAndInitAnnotationsMap(HttpServletRequest request) {
        Method method = getMethod(request);
        if (method == null) {
            return Collections.EMPTY_SET;
        }
        Annotation[] declaredAnnotationsInMethod = method.getDeclaredAnnotations();
        Annotation[] declaredAnnotationsInClass = method.getDeclaringClass().getDeclaredAnnotations();
        if (declaredAnnotationsInMethod == null && declaredAnnotationsInClass == null) {
            annotationsMap.put(request.getMethod() + request.getRequestURI(), Collections.EMPTY_SET);
            return Collections.EMPTY_SET;
        }

        HashSet<Annotation> annotations = Optional.ofNullable(declaredAnnotationsInClass).map(Sets::newHashSet).orElse(new HashSet<>());
        HashSet<Annotation> annotationsMethod = Optional.ofNullable(declaredAnnotationsInMethod).map(Sets::newHashSet).orElse(new HashSet<>());
        Set<Annotation> annotationsClassExclude = annotations.stream()
                .filter(annotation -> annotationsMethod.stream()
                        .filter(annotationMethod -> Objects.equals(annotationMethod.getClass(), annotation.getClass()))
                        .findAny().orElse(null) == null)
                .collect(Collectors.toSet());
        Set<Annotation> unionAll = Sets.union(annotationsClassExclude, annotationsMethod).stream()
                .filter(annotation ->
                        annotation instanceof RequiresAuthentication ||
                                annotation instanceof RequiresRoles ||
                                annotation instanceof RequiresPermissions

                ).collect(Collectors.toSet());
        log.info("shiro初始化：method：{},uri:{},annotation:{}", request.getMethod(), request.getRequestURI(), unionAll);
        return unionAll;
    }

    private Method getMethod(HttpServletRequest request) {
        try {
            if (initHandlerMappingFlag) {
                buildHandlerMappings();
            }
            return Optional.ofNullable(handlerMapping.getHandler(request))
                    .map(handler -> handler.getHandler()).filter(Objects::nonNull)
                    .map(object -> ((HandlerMethod) object).getMethod()).orElse(null);
        } catch (Exception e) {
            log.error("get method fail", e);
        }
        return null;
    }


    private synchronized void buildHandlerMappings() {
        if (initHandlerMappingFlag) {
            initHandlerMappingFlag = false;
            initHandlerMappings();
        }
    }

    private void initHandlerMappings() {
        Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                SpringFactory.getApplicationContext(), HandlerMapping.class, true, false);
        if (!matchingBeans.isEmpty()) {
            for (HandlerMapping handlerMapping : matchingBeans.values()) {
                if (handlerMapping instanceof RequestMappingHandlerMapping) {
                    this.handlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                    break;
                }
            }
        }
    }

}
