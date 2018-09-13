package com.example.producer.user.controller;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.producer.user.service.UserService;
import com.example.tools.data.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户服务控制层
 * @author hzh 2018/9/12 16:01
 */
@RestController
@RequestMapping("/system-user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询系统用户列表
     * @param queryCondition 系统用户列表查询及分页条件
     * @return 返回系统用户分页信息
     */
    @PostMapping
    public Page<SearchUsersCondition, User> users(@RequestBody Page<SearchUsersCondition, User> queryCondition) {
        System.out.println("qingqiu");
        return userService.users(queryCondition);
    }
}
