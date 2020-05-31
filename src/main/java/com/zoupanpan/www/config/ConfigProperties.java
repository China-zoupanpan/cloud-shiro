package com.zoupanpan.www.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zoupanpan
 * @version 2020/5/16 18:06
 */
@ConfigurationProperties(prefix = "com.spring.www")
public class ConfigProperties {
    private String mode;
    private int time;
    private String name;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
