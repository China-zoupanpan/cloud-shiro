package com.zoupanpan.www.login.dao;

import com.zoupanpan.www.base.dao.BaseDao;
import com.zoupanpan.www.login.domain.SessionEntity;
import org.springframework.stereotype.Repository;

/**
 * @author zoupanpan
 * @version 2020/3/28 10:41
 */
@Repository
public interface SessionEntityDao extends BaseDao<SessionEntity, Long> {

}
