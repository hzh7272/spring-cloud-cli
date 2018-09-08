package com.example.producer.user.dao;

import com.example.producer.user.model.User;
import com.example.commons.core.base.BasicMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统用户表数据操作接口
 * @author hzh 2018-09-09 00:02
 */
@Mapper
@Repository
public interface UserMapper extends BasicMapper<User, Long> {

}
