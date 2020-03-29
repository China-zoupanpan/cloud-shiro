package com.zoupanpan.www.login.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoupanpan.www.base.domain.BaseEntity;
import com.zoupanpan.www.util.JSONUtil;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author zoupanpan
 * @version 2020/3/28 10:36
 */
@Entity
@Table(name = "session")
public class SessionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "startTimestamp")
    private Date startTimestamp;
    @Column(name = "stopTimestamp")
    private Date stopTimestamp;
    @Column(name = "lastAccessTime")
    private Date lastAccessTime;
    @Column(name = "timeout")
    private long timeout;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "host")
    private String host;

    public SessionEntity() {
    }

    public SessionEntity(SimpleSession session) {
//        this.id = NumberUtils.toLong(Objects.toString(session.getId()));
        this.startTimestamp = session.getStartTimestamp();
        this.stopTimestamp = session.getStopTimestamp();
        this.lastAccessTime = session.getLastAccessTime();
        this.timeout = session.getTimeout();
        this.expired = session.isExpired();
        this.host = session.getHost();
    }


    public static SimpleSession getSimpleSessionFromEntity(SessionEntity session) {
        SimpleSession simpleSession = new SimpleSession();
        try {
            Map map = JSONUtil.parseObject(session.getContent(), Map.class);
            JSONObject jsonObject = (JSONObject) map.get(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            JSONArray realmNames = (JSONArray) jsonObject.get("realmNames");
            PrincipalCollection p = new SimplePrincipalCollection(jsonObject.get("primaryPrincipal"), Objects.toString(realmNames.get(0)));
            map.put(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, p);
            simpleSession.setAttributes(map);
        } catch (Exception e) {
            System.err.println("SessionEntity getSimpleSessionFromEntity" + e.getCause());
        }
        simpleSession.setTimeout(session.getTimeout());
        simpleSession.setStartTimestamp(session.getStartTimestamp());
        simpleSession.setStopTimestamp(session.getStopTimestamp());
        simpleSession.setLastAccessTime(session.getLastAccessTime());
        simpleSession.setId(session.getId());
        simpleSession.setExpired(session.isExpired());
        simpleSession.setHost(session.getHost());
        return simpleSession;
    }

    public void merge(SimpleSession session) {
        this.content = JSONUtil.writeObjectToJson(session.getAttributes());
        this.startTimestamp = session.getStartTimestamp();
        this.stopTimestamp = session.getStopTimestamp();
        this.lastAccessTime = session.getLastAccessTime();
        this.timeout = session.getTimeout();
        this.expired = session.isExpired();
        this.host = session.getHost();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getStopTimestamp() {
        return stopTimestamp;
    }

    public void setStopTimestamp(Date stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
