package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;

import java.util.function.Predicate;

/**
 * 基础数据库表生成工具
 * @author hzh 2018/9/5 上午12:23
 */
public class BasicMySqlDdlGenerator extends AbstractMySqlDdlGenerator {

	private MySqlDbInfo mySqlDbInfo;
	private GeneratorInfo generatorInfo;

	public BasicMySqlDdlGenerator(MySqlDbInfo mySqlDbInfo, GeneratorInfo generatorInfo) {
		this.mySqlDbInfo = mySqlDbInfo;
		this.generatorInfo = generatorInfo;
	}

	/**
	 * 生成数据库ddl建表SQL
	 * @return 返回建表SQL
	 */
	@Override
	protected String generatorDdlSql() {
		StringBuilder ddlSql = new StringBuilder();

		this.createTableSql(ddlSql);
		return ddlSql.toString();
	}

	/**
	 * 建表SQL
	 * @param ddlSql
	 */
	private void createTableSql(StringBuilder ddlSql) {
		ddlSql.append("CREATE TABLE " + generatorInfo.getTable().getTableName() + "（\n");

		Predicate<Column> hasDefaultValue = column -> null != column.getDefaultVale() && !"".equals(column.getDefaultVale());;

		generatorInfo.getColumnList().forEach(column ->
				ddlSql.append(column.getColumnName() + " ")
						.append(column.getJdbcType() + "(")
						.append(column.getLength() + ")")
						.append(column.getNotNull() ? " NOT NULL" : " ")
						.append(column.getAutoIncrement() ? " AUTO_INCREMENT" :
								hasDefaultValue.test(column) ? " DEFAULT '" + column.getDefaultVale() + "'" : " DEFAULT NULL")
						.append(" COMMENT '" + column.getComment() + "',\n"));
	}

	/**
	 * 执行建表SQL
	 * @param ddlSql 建表ddl语句
	 */
	@Override
	protected void executeSql(String ddlSql) {
		System.out.println(ddlSql);
	}
}
