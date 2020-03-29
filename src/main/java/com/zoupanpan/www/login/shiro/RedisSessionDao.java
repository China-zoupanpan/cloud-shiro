package com.zoupanpan.www.login.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author zoupanpan
 * @version 2020/3/28 17:13
 */
public class RedisSessionDao extends CachingSessionDAO {

    private Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
    private RedisTemplate redisTemplate;
    private ValueOperations valueOperations;
    private static final String SHIRO_SESSION_PREFIX = "shiro:";

    public RedisSessionDao(RedisConnectionFactory redisConnectionFactory) {
        this.initRedisTemplate(redisConnectionFactory);
    }

    public void initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this.redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.java());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        redisTemplate.afterPropertiesSet();
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    protected void doUpdate(Session session) {
        logger.info("session更新 sessionId:{}", session.getId());
        if (session instanceof ValidatingSession) {
            if (((ValidatingSession) session).isValid()) {
                updateCache(session);
            } else {
                redisTemplate.delete(SHIRO_SESSION_PREFIX + session.getId());
            }
        } else {
            updateCache(session);
        }

    }

    private void updateCache(Session session) {
        //不登陆的账号不能保存session到redis
        if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
            logger.info("session持久化更新 sessionId:{}", session.getId());
            if (session.getTimeout() > 0) {
                valueOperations.set(SHIRO_SESSION_PREFIX + session.getId(), session, session.getTimeout() / 1000, TimeUnit.SECONDS);
            } else {
                valueOperations.set(SHIRO_SESSION_PREFIX + session.getId(), session, 30, TimeUnit.DAYS);
            }
        }
    }

    @Override
    protected void doDelete(Session session) {
        logger.info("删除session:{}", session);
        redisTemplate.delete(SHIRO_SESSION_PREFIX + session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        logger.info("创建session session:{}", session);
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
            logger.info("session持久化 sessionId:{}", session.getId());
            if (session.getTimeout() > 0) {
                valueOperations.set(SHIRO_SESSION_PREFIX + session.getId(), session, session.getTimeout() / 1000, TimeUnit.SECONDS);
            } else {
                valueOperations.set(SHIRO_SESSION_PREFIX + session.getId(), session, 30, TimeUnit.DAYS);
            }

        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) valueOperations.get(SHIRO_SESSION_PREFIX + sessionId);
    }
}
