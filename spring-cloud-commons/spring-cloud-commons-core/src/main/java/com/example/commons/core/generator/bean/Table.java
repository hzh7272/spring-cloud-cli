package com.example.commons.core.generator.bean;

import lombok.Data;

/**
 * 数据库表数据模型
 * @author hzh 2018/8/26 下午10:32
 */
@Data
public class Table {

	private String tableName;
	private String comment;
	private String charset;
	private String engine;
	private Boolean updateAble;
	private Boolean dropIfExits;
}
