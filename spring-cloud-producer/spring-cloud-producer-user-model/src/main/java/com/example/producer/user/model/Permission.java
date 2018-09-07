package com.example.producer.user.model;

import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;

/**
 * 系统权限数据模型
 * @author hzh 2018/9/7 17:22
 */
@Data
@Table(name = "permission", comment = "系统权限表")
public class Permission {

    @TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "系统权限ID")
    private Long id;
    @TableColumn(comment = "上级系统权限ID")
    private Long topId;
    @TableColumn(comment = "系统权限编码")
    private String code;
    @TableColumn(comment = "系统权限类型")
    private String type;
    @TableColumn(length = 128, comment = "资源链接")
    private String url;
    private String permissionExpression;
    private Integer sort;
}
