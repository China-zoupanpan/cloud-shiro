package com.zoupanpan.www.login.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * @author zoupanpan
 * @version 2020/3/29 17:14
 */
public class JDBCWebSecurityManager extends DefaultWebSecurityManager {


    public JDBCWebSecurityManager(Realm realm, SessionDAO jdbcSessionDao) {
        // 关联realm.
        this.setRealm(realm);
        this.setSessionManager(new DefaultWebSecurityManager());
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdCookie(new SimpleCookie(ShiroConstant.LOGIN_TICKET_NAME));
        defaultWebSessionManager.setSessionDAO(jdbcSessionDao);
        this.setSessionManager(defaultWebSessionManager);
    }
}
