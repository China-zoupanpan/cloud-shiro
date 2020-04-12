package com.zoupanpan.www.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author zoupanpan
 * @version 2020/4/9 17:45
 */
public class CustomizeEnvironmentPostProcessor implements EnvironmentPostProcessor, PriorityOrdered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("=================environment=============");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
