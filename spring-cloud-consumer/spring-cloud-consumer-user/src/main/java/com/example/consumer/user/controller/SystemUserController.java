package com.example.consumer.user.controller;

import com.example.consumer.user.feign.UserClient;
import com.example.producer.user.dto.SearchUsersCondition;
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

    private UserClient userClient;

    public SystemUserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public Page<SearchUsersCondition, User> systemUserList() {
        return userClient.users(new Page<>());
    }
}
