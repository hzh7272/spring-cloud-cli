package com.example.commons.core.generator.bean;

import lombok.Data;

/**
 * 数据库字段模型
 * @author hzh 2018/8/26 下午10:24
 */
@Data
public class Column {

	private Boolean primaryKey;
	private Boolean autoIncrement;
	private String fieldName;
	private String columnName;
	private String columnNameSimple;
	private String jdbcType;
	private Integer length;
	private Integer decimalPoint;
	private Boolean notNull;
	private String comment;
	private Object defaultVale;
	private Boolean indexKey;
	private Boolean uniqueKey;
	private Object[] enumValues;
}
