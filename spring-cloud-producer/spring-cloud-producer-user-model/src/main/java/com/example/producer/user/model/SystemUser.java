package com.example.producer.user.model;

import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;

/**
 * 系统用户数据模型
 * @author hzh 2018/9/6 17:27
 */
@Data
@Table(name = "system_user", comment = "系统用户表")
public class SystemUser {

    @TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "系统用户ID")
    private Long id;
    @TableColumn(notNull = true, length = 32, comment = "账号")
    private String account;
    private String mobile;
    private String username;
    private String avatarUrl;
    private String password;
    private Integer superAdmin;
}
