package com.zoupanpan.www.base.dao;

import com.zoupanpan.www.util.SpringFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author zoupanpan
 * @version 2020/3/22 12:42
 */
@NoRepositoryBean
public interface  BaseDao<T,ID> extends JpaRepository<T,ID>{


    default JdbcTemplate getJdbcTemplate(){
        return SpringFactory.getBean("jdbcTemplate");
    }

}
