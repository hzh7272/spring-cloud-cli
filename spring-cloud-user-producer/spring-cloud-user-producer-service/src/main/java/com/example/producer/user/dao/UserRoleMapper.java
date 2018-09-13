package com.example.producer.user.dao;

import com.example.producer.user.model.UserRole;
import com.example.commons.core.base.BasicMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联表数据操作接口
 * @author hzh 2018-09-09 00:03
 */
@Mapper
@Repository
public interface UserRoleMapper extends BasicMapper<UserRole, Long> {

}
