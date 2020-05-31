package com.zoupanpan.www;


import com.zoupanpan.plugin.kafka.annotation.EnableKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
@EnableKafka
public class CloudShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudShiroApplication.class, args);
    }

}
