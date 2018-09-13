package com.example.producer.user.service.impl;

import com.example.producer.user.dao.UserMapper;
import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.producer.user.service.UserService;
import com.example.tools.data.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务相关业务层
 * @author hzh 2018/9/12 16:13
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 查询系统用户列表
     * @param queryCondition 系统用户列表查询及分页条件
     * @return 返回系统用户分页信息
     */
    @Override
    public Page<SearchUsersCondition, User> users(Page<SearchUsersCondition, User> queryCondition) {
        List<User> userList = new ArrayList<>();
        userList.add(userMapper.findById(1L));
        queryCondition.setData(userList);
        return queryCondition;
    }
}
