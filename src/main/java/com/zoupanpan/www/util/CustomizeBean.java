package com.zoupanpan.www.util;

import org.springframework.context.annotation.Bean;

/**
 * @author zoupanpan
 * @version 2020/5/16 14:22
 */
public interface CustomizeBean {

    @Bean
    default SimpleBean getSimpleBean() {
        return new SimpleBean();
    }
}
