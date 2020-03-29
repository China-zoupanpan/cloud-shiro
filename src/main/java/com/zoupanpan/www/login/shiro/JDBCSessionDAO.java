package com.zoupanpan.www.login.shiro;

import com.zoupanpan.www.base.bean.BaseBean;
import com.zoupanpan.www.login.dao.SessionEntityDao;
import com.zoupanpan.www.login.domain.SessionEntity;
import com.zoupanpan.www.util.JSONUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.RandomSessionIdGenerator;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoupanpan
 * @version 2020/3/28 10:31
 */
//@Component
public class JDBCSessionDAO extends AbstractSessionDAO {

    public JDBCSessionDAO(SessionEntityDao sessionEntityDao) {
        super.setSessionIdGenerator(new RandomSessionIdGenerator());
        this.sessionEntityDao = sessionEntityDao;
    }

    private SessionEntityDao sessionEntityDao;

    @Override
    @Transactional
    protected Serializable doCreate(Session session) {
//        Serializable sessionId = generateSessionId(session);
//        assignSessionId(session, sessionId);
        SessionEntity entity = new SessionEntity((SimpleSession) session);
        entity.setContent(JSONUtil.writeObjectToJson(((SimpleSession) session).getAttributes()));
        entity.setCreateTime(new Date());
        entity.setIsValid(BaseBean.TRUE_INT);
        entity.setUpdateTime(new Date());
        SessionEntity save = sessionEntityDao.save(entity);
        assignSessionId(session, save.getId());
        return save.getId();
    }

    @Override
    @Transactional(readOnly = true)
    protected Session doReadSession(Serializable sessionId) {
        return sessionEntityDao.findById(NumberUtils.toLong(Objects.toString(sessionId, null)))
                .map(entity -> SessionEntity.getSimpleSessionFromEntity(entity)).orElse(null);
    }

    @Override
    @Transactional
    public void update(Session session) throws UnknownSessionException {
        if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
            sessionEntityDao.findById(NumberUtils.toLong(Objects.toString(session.getId(), null))).filter(entity -> {
                if (Objects.isNull(entity)) {
                    throw new UnknownSessionException("session id " + session.getId() + " not unknown");
                }
                return true;
            }).ifPresent(entity -> {
                entity.merge((SimpleSession) session);
                entity.setUpdateTime(new Date());
                sessionEntityDao.save(entity);
            });
        }
    }

    @Override
    @Transactional
    public void delete(Session session) {
        sessionEntityDao.findById(NumberUtils.toLong(Objects.toString(session.getId(), null))).ifPresent(entity ->
                sessionEntityDao.delete(entity)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Session> getActiveSessions() {
        List<SessionEntity> all = sessionEntityDao.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            return all.stream().map(entity -> JSONUtil.parseObject(entity.getContent(), Session.class))
                    .filter(Objects::isNull).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

}
