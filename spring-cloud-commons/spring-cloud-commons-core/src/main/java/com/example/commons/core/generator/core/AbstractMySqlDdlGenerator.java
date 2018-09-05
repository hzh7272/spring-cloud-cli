package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.TableColumn;

import java.util.List;

/**
 * 数据库表
 * @version 1.0.0
 * @author hzh 2018/8/26 下午9:16
 */
public abstract class AbstractMySqlDdlGenerator {

	/**
	 * 生成数据库ddl建表SQL
	 * @return 返回建表SQL
	 */
	protected abstract String generatorDdlSql();

	/**
	 * 生成数据库增量表SQL
	 * @param tableColumnList 数据库字段集合
	 * @return 返回增量表SQL
	 */
	protected abstract String generatorAlterSql(List<TableColumn> tableColumnList);

	/**
	 * 执行建表SQL
	 * @param ddlSql 建表ddl语句
	 */
	protected abstract void executeSql(String ddlSql);

	/**
	 * 生成数据库表
	 */
	public final void generatorTable() {
		String ddlSql = this.generatorDdlSql();
		this.executeSql(ddlSql);
	}
}
