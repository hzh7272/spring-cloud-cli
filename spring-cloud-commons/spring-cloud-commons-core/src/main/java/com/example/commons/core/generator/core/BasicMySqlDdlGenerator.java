package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Table;

import java.util.ArrayList;
import java.util.List;
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
		Table table = generatorInfo.getTable();

		ddlSql.append("CREATE TABLE " + table.getTableName() + " (\n");

		// 是否有默认值
		Predicate<Column> hasDefaultValue = column -> null != column.getDefaultVale() && !"".equals(column.getDefaultVale());
		// 是否为浮点类型
		Predicate<Column> isFloatType = column -> "DECIMAL".equals(column.getJdbcType());
		// 是否为无长度类型
		Predicate<Column> isNoLengthType = column -> "TEXT".equals(column.getJdbcType()) || "TIMESTAMP".equals(column.getJdbcType());

		List<Column> primaryKeyList = new ArrayList<>();
		List<Column> indexKeyList = new ArrayList<>();

		// 拼装ddl语句
		generatorInfo.getColumnList().forEach(column -> {
			// 判断是否为主键
			if (column.getPrimaryKey()) {
				primaryKeyList.add(column);
			}
			if (column.getIndexKey()) {
				indexKeyList.add(column);
			}

			ddlSql.append("\t" + column.getColumnName() + " ")
					.append(column.getJdbcType())
					.append(isNoLengthType.test(column) ? "" : "(")
					.append(isNoLengthType.test(column) ? "" : column.getLength())
					.append(isNoLengthType.test(column) ? "" : isFloatType.test(column) ? ", " + column.getDecimalPoint() : "")
					.append(isNoLengthType.test(column) ? "" : ")")
					.append(column.getNotNull() ? " NOT NULL" : "")
					.append(column.getAutoIncrement() ? " AUTO_INCREMENT" :
							hasDefaultValue.test(column) ? " DEFAULT '" + column.getDefaultVale() + "'" : " DEFAULT NULL")
					.append("".equals(column.getComment()) ? "" : " COMMENT '" + column.getComment() + "',\n");
		});

		createLastDdlSql(ddlSql, table, primaryKeyList, indexKeyList);

	}

	/**
	 * 创建表主键，索引语句
	 * @param ddlSql
	 * @param table
	 * @param primaryKeyList
	 * @param indexKeyList
	 */
	private void createLastDdlSql(StringBuilder ddlSql, Table table, List<Column> primaryKeyList, List<Column> indexKeyList) {
		// 主键
		primaryKeyList.forEach(primaryKey -> ddlSql.append("\t").append("PRIMARY KEY (").append(primaryKey.getColumnName()).append(") USING BTREE,\n"));

		// 索引
		indexKeyList.forEach(indexKey -> ddlSql.append("\t").append(indexKey.getUniqueKey() ? "UNIQUE " : "").append("KEY (").append(indexKey.getColumnName()).append(") USING BTREE,\n"));

		ddlSql.deleteCharAt(ddlSql.length() - 1);
		ddlSql.deleteCharAt(ddlSql.length() - 1);


		ddlSql.append("\n) ENGINE=" + table.getEngine())
				.append(" AUTO_INCREMENT=1 DEFAULT CHARSET=" + table.getCharset())
				.append(" ROW_FORMAT=COMPACT")
				.append("".equals(table.getComment()) ? "" : " COMMENT='" + table.getComment() + "'")
				.append(";");
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
