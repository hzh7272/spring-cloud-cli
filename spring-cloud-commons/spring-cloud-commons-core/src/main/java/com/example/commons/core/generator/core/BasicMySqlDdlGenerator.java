package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Table;
import com.example.commons.core.generator.bean.TableColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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
	 * @param ddlSql ddlSql
	 */
	private void createTableSql(StringBuilder ddlSql) {
		Table table = generatorInfo.getTable();

		ddlSql.append("CREATE TABLE ").append(table.getTableName()).append(" (\n");

		// 是否有默认值
		Predicate<Column> hasDefaultValue = column -> null != column.getDefaultVale() && !"".equals(column.getDefaultVale());
		// 是否为浮点类型
		Predicate<Column> isFloatType = column -> "DECIMAL".equals(column.getJdbcType());
		// 是否为无长度类型
		Predicate<Column> isNoLengthType = column -> "TEXT".equals(column.getJdbcType()) || "TIMESTAMP".equals(column.getJdbcType());

		Function<Column, String> getDecimalPoint = column -> isFloatType.test(column) ? ", " + column.getDecimalPoint() : "";
		Function<Column, String> getDefaultValue = column -> hasDefaultValue.test(column) ? " DEFAULT '" + column.getDefaultVale() + "'" : " DEFAULT NULL";

		List<Column> primaryKeyList = new ArrayList<>();
		List<Column> indexKeyList = new ArrayList<>();

		// 拼装ddl语句
		generatorInfo.getColumnList().forEach(column -> {
			// 判断是否为主键
			if (column.getPrimaryKey()) {
				primaryKeyList.add(column);
			}
			// 判断是否为索引
			if (column.getIndexKey()) {
				indexKeyList.add(column);
			}

			ddlSql.append("\t").append(column.getColumnName()).append(" ")
					.append(column.getJdbcType())
					.append(isNoLengthType.test(column) ? "" : "(")
					.append(isNoLengthType.test(column) ? "" : column.getLength())
					.append(isNoLengthType.test(column) ? "" : getDecimalPoint.apply(column))
					.append(isNoLengthType.test(column) ? "" : ")")
					.append(column.getNotNull() ? " NOT NULL" : "TIMESTAMP".equals(column.getJdbcType()) ? " NULL" : "")
					.append(column.getAutoIncrement() ? " AUTO_INCREMENT" : getDefaultValue.apply(column))
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


		ddlSql.append("\n) ENGINE=").append(table.getEngine())
				.append(" AUTO_INCREMENT=1 DEFAULT CHARSET=").append(table.getCharset())
				.append(" ROW_FORMAT=COMPACT")
				.append("".equals(table.getComment()) ? "" : " COMMENT='" + table.getComment() + "'")
				.append(";");
	}

	/**
	 * 生成数据库增量表SQL
	 * @param tableColumnList 数据库字段集合
	 * @return 返回增量表SQL
	 */
	@Override
	protected String generatorAlterSql(List<TableColumn> tableColumnList) {
		return null;
	}

	/**
	 * 执行建表SQL
	 * @param ddlSql 建表ddl语句
	 */
	@Override
	protected void executeSql(String ddlSql) {
		try (Connection connection = DriverManager.getConnection(mySqlDbInfo.getUrl(), mySqlDbInfo.getUsername(), mySqlDbInfo.getPassword());
				PreparedStatement preparedStatement = connection.prepareStatement(ddlSql);) {
//			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
