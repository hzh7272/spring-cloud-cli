package com.example.producer.user.dao;

import com.example.producer.user.model.RolePermission;
import com.example.commons.core.base.BasicMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色权限关联表数据操作接口
 * @author hzh 2018-09-09 00:05
 */
@Mapper
@Repository
public interface RolePermissionMapper extends BasicMapper<RolePermission, Long> {

}
