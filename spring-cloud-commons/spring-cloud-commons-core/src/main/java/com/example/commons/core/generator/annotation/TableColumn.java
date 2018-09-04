package com.example.commons.core.generator.annotation;

import java.lang.annotation.*;

/**
 * 数据库表字段信息注解
 * @version 1.0.0
 * @author hzh 2018/8/26 下午8:15
 */
@Documented
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {

	/**
	 * 是否主键
	 * @return 默认 false
	 */
	boolean primaryKey() default false;

	/**
	 * 是否自动增长
	 * @return 默认 false
	 */
	boolean autoIncrease() default false;

	/**
	 * 是否必填
	 * @return 默认 false
	 */
	boolean notNull() default false;

	/**
	 * 是否为索引
	 * @return 默认 false
	 */
	boolean index() default false;

	/**
	 * 是否唯一索引
	 * @return 默认 false
	 */
	boolean uniqueKey() default false;

	/**
	 * 是否长文本类型
	 * @return 默认 false
	 */
	boolean bigText() default false;

	/**
	 * 字段长度
	 * @return 默认 11
	 */
	int length() default 11;

	/**
	 * 小数点位数
	 * @return 默认 0
	 */
	int decimalPoint() default 0;

	/**
	 * 字段注释
	 * @return 默认空
	 */
	String comment() default "";

	/**
	 * 默认值
	 * @return 默认空
	 */
	String defaultValue() default "";
}
