package com.example.producer.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 用户服务
 * @author hzh 2018/9/10 11:07
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class UserProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProducerApplication.class, args);
    }

}
