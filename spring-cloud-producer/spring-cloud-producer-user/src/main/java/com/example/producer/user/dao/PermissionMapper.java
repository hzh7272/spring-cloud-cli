package com.example.producer.user.dao;

import com.example.producer.user.model.Permission;
import com.example.commons.core.base.BasicMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统权限表数据操作接口
 * @author hzh 2018-09-09 00:08
 */
@Mapper
@Repository
public interface PermissionMapper extends BasicMapper<Permission, Long> {

}
