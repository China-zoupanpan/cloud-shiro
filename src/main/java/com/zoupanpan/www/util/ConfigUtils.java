package com.zoupanpan.www.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zoupanpan
 * @version 2020/4/11 18:13
 */
public class ConfigUtils {

    private Properties properties;

    private static final String url = "config.properties";

    private static class Holder {
        private static ConfigUtils INSTANCE = new ConfigUtils();
    }

    public static ConfigUtils getInstance() {
        return Holder.INSTANCE;
    }

    private ConfigUtils() {
        ResourceLoader resourceLoader = new DefaultResourceLoader(ConfigUtils.class.getClassLoader());
        Resource resource = resourceLoader.getResource(url);
        try {
            this.properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException("can't not find file?file name " + url);
        }
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        String myconfig = getInstance().getProperty("myconfig");
        System.out.println(myconfig);
    }

}
