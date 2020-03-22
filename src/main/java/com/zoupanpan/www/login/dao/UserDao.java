package com.zoupanpan.www.login.dao;

import com.zoupanpan.www.base.dao.BaseDao;
import com.zoupanpan.www.login.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author zoupanpan
 * @version 2020/3/22 15:33
 */
@Repository
public interface UserDao  extends BaseDao<User,Long> {

    User findByName(String name);


    @Transactional(readOnly = true)
    default  User queryUser(Long id){
        String sql="select * from user where id ="+id;
        if(Objects.nonNull(id)){
            RowMapper<User> rowMapper= new BeanPropertyRowMapper<>(User.class);
            List<User> userList = getJdbcTemplate().query(sql, rowMapper);
            return CollectionUtils.isEmpty(userList)?null:userList.get(0);
        }
        return null;
    }
}
