package com.example.commons.core.generator.annotation;

import java.lang.annotation.*;

/**
 * 数据库表信息注解
 * @author hzh 2018/8/26 下午8:30
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	/**
	 * 数据库表名
	 * @return 默认空，自动生成
	 */
	String name();

	/**
	 * 注释
	 * @return 默认空
	 */
	String comment();

	/**
	 * 是否更新信息
	 * @return 默认 false
	 */
	boolean updateAble() default false;

	/**
	 * 是否重建表
	 * @return 默认 false
	 */
	boolean dropIfExist() default false;

	/**
	 * 数据库表编码
	 * @return 默认utf8mb4
	 */
	String charset() default "utf8mb4";

	/**
	 * 数据库引擎
	 * @return 默认InnoDB
	 */
	String engine() default "InnoDB";
}
