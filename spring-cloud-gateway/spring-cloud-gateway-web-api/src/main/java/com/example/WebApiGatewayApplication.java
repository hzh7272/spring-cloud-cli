package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * web api 服务网关
 * @author hzh 2018/9/13 16:16
 */
@EnableZuulProxy
@SpringCloudApplication
public class WebApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiGatewayApplication.class, args);
    }
}
