package com.zoupanpan.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@ComponentScan(value = "com.zoupanpan")
@SpringBootApplication
public class CloudShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudShiroApplication.class, args);
    }

}
