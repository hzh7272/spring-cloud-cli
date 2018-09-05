package com.example.commons.core.generator.bean;

import lombok.Data;

/**
 * 数据库实体类信息
 * @author hzh 2018/9/5 下午9:01
 */
@Data
public class Model {

	private String packageName;
	private String className;
	private String classFullName;
}
