package com.example.commons.core.generator.bean;

import lombok.Data;

/**
 * 数据库表字段
 * @author hzh 2018/9/6 上午12:20
 */
@Data
public class TableColumn {

	private String field;
	private String jdbcType;
	private Integer length;
	private Boolean notNull;
	private Object defaultValue;
}
