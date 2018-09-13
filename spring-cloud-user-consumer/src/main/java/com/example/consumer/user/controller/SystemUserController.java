package com.example.consumer.user.controller;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.feign.UserFeignClient;
import com.example.producer.user.model.User;
import com.example.tools.data.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制层
 * @author hzh 2018/9/13 10:27
 */
@RestController
@RequestMapping("/v1/system-user")
public class SystemUserController {

    private UserFeignClient userFeignClient;

    public SystemUserController(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @GetMapping
    public Page<SearchUsersCondition, User> systemUserList() {
        return userFeignClient.users(new Page<>());
    }
}
