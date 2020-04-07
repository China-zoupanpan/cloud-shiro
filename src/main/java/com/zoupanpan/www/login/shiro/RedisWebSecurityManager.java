package com.zoupanpan.www.login.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zoupanpan
 * @version 2020/3/29 17:13
 */
public class RedisWebSecurityManager extends DefaultWebSecurityManager {


    public RedisWebSecurityManager(Realm realm, RedisSessionDao redisSessionDao) {
        //添加了一层本地缓存 未做到期处理
        super.setCacheManager(new AbstractCacheManager() {
            protected Cache<Serializable, Session> createCache(String name) throws CacheException {
                return new MapCache(name, new ConcurrentHashMap());
            }
        });
        // 关联realm.
        super.setRealm(realm);
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdCookie(new SimpleCookie(ShiroConstant.LOGIN_TICKET_NAME));
        defaultWebSessionManager.setSessionDAO(redisSessionDao);
        super.setSessionManager(defaultWebSessionManager);
    }
}
