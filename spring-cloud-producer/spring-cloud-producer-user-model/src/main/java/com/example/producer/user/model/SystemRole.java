package com.example.producer.user.model;

import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;

/**
 * 系统角色数据模型
 * @author hzh 2018/9/7 15:41
 */
@Data
@Table(name = "system_role", comment = "系统角色表")
public class SystemRole {

    @TableColumn(primaryKey = true, autoIncrease = true, notNull = true, comment = "系统角色ID")
    private Long id;
    @TableColumn(notNull = true, length = 32, comment = "角色名称")
    private String name;
    @TableColumn(notNull = true, comment = "角色编码")
    private String code;
    @TableColumn(notNull = true, defaultValue = "99", comment = "角色等级")
    private Integer level;
}
