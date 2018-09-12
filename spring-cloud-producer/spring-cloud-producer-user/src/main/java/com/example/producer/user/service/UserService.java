package com.example.producer.user.service;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.tools.data.Page;

import java.util.List;

/**
 * 用户服务相关业务层
 * @author hzh 2018/9/10 11:33
 */
public interface UserService {

    /**
     * 查询系统用户列表
     * @param searchUsersCondition 系统用户列表查询条件
     * @param page 分页条件
     * @return 返回系统用户分页信息
     */
    List<User> users(SearchUsersCondition searchUsersCondition, Page page);
}
