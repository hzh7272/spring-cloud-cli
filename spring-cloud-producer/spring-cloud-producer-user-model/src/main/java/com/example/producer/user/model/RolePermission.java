package com.example.producer.user.model;

import com.example.commons.core.base.BasicModel;
import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联数据模型
 * @author hzh 2018/9/8 下午11:28
 */
@EqualsAndHashCode(callSuper = false)
@Table(name = "role_permission", comment = "角色权限关联表")
public class RolePermission extends BasicModel {

	@TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "角色权限关联ID")
	private Long id;
	@TableColumn(comment = "系统角色ID")
	private Long roleId;
	@TableColumn(comment = "系统权限ID")
	private Long permissionId;
}
