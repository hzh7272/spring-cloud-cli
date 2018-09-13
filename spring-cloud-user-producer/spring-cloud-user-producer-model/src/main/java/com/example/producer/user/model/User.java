package com.example.producer.user.model;

import com.example.commons.core.base.BasicModel;
import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户数据模型
 * @author hzh 2018/9/6 17:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "user", comment = "系统用户表")
public class User extends BasicModel {

    @TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "系统用户ID")
    private Long id;
    @TableColumn(notNull = true, length = 32, comment = "账号")
    private String account;
    @TableColumn(comment = "手机")
    private String mobile;
    @TableColumn(length = 32, notNull = true, comment = "用户名")
    private String username;
    @TableColumn(length = 128, comment = "头像")
    private String avatarUrl;
    @TableColumn(length = 128, notNull = true, comment = "密码")
    private String password;
    @TableColumn(length = 1, notNull = true, defaultValue = "0", comment = "超级管理员（1：是，0：否）")
    private Integer superAdmin;
}
