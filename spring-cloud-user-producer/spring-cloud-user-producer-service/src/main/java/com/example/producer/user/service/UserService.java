package com.example.producer.user.service;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.tools.data.Page;

/**
 * 用户服务相关业务层
 * @author hzh 2018/9/10 11:33
 */
public interface UserService {

    /**
     * 查询系统用户列表
     * @param queryCondition 系统用户列表查询及分页条件
     * @return 返回系统用户分页信息
     */
    Page<SearchUsersCondition, User> users(Page<SearchUsersCondition, User> queryCondition);
}
