package com.example.consumer.user.feign;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.model.User;
import com.example.tools.data.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务客户端
 * @author hzh 2018/9/13 10:20
 */
@FeignClient(name = "user-producer")
public interface UserClient {

    /**
     * 查询系统用户列表
     * @param queryCondition 系统用户列表查询及分页条件
     * @return 返回系统用户分页信息
     */
    @PostMapping(value = "/system-user")
    Page<SearchUsersCondition, User> users(@RequestBody Page<SearchUsersCondition, User> queryCondition);

}
