package com.example.producer.user.model;

import com.example.commons.core.base.BasicModel;
import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import com.example.producer.user.constant.PermissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统权限数据模型
 * @author hzh 2018/9/7 17:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "permission", comment = "系统权限表")
public class Permission extends BasicModel {

    @TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "系统权限ID")
    private Long id;
    @TableColumn(comment = "上级系统权限ID")
    private Long topId;
    @TableColumn(comment = "系统权限编码")
    private String code;
    @TableColumn(comment = "系统权限类型")
    private PermissionType type;
    @TableColumn(length = 128, comment = "资源链接")
    private String url;
    @TableColumn(length = 32, notNull = true, comment = "权限表达式")
    private String permissionExpression;
    @TableColumn(length = 2, defaultValue = "99", comment = "权重")
    private Integer sort;
}
