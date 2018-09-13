package com.example.producer.user.hystrix;

import com.example.producer.user.dto.SearchUsersCondition;
import com.example.producer.user.feign.UserFeignClient;
import com.example.producer.user.model.User;
import com.example.tools.data.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务接口断路器
 * @author hzh 2018/9/13 14:18
 */
@Slf4j
@Component
public class UserFeignClientHystrix implements UserFeignClient {

    /**
     * 查询系统用户列表
     * @param queryCondition 系统用户列表查询及分页条件
     * @return 返回系统用户分页信息
     */
    @Override
    public Page<SearchUsersCondition, User> users(@RequestBody Page<SearchUsersCondition, User> queryCondition) {
        log.error("查询系统用户列表失败");
        return queryCondition;
    }
}
