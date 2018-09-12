package com.example.producer.user.controller;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.producer.user.service.UserService;
import com.example.tools.data.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * @param searchUsersCondition 系统用户列表查询条件
     * @param page 分页条件
     * @return 返回系统用户分页信息
     */
    @GetMapping
    public List<User> users(SearchUsersCondition searchUsersCondition, Page page) {
        return userService.users(searchUsersCondition, page);
    }
}
