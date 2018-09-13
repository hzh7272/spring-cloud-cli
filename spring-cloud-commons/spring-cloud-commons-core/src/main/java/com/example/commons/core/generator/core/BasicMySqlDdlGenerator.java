package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Table;
import com.example.commons.core.generator.bean.TableColumn;
import com.example.commons.core.generator.support.JdbcType;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 基础数据库表生成工具
 * @author hzh 2018/9/5 上午12:23
 */
@Slf4j
public class BasicMySqlDdlGenerator extends AbstractMySqlDdlGenerator {

	public BasicMySqlDdlGenerator(MySqlDbInfo mySqlDbInfo, GeneratorInfo generatorInfo) {
		super(mySqlDbInfo, generatorInfo);
	}

	/**
	 * 判断数据库表是否存在
	 * @param connection 数据库链接
	 * @param tableName 数据库表名
	 * @return 返回true则存在，false则不存在
	 * @throws SQLException SQL异常
	 */
	@Override
	protected boolean isTableExits(Connection connection, String tableName) throws SQLException {
		PreparedStatement showTablesPs = null;
		ResultSet showTablesRs = null;
		try {
			showTablesPs = connection.prepareStatement("SHOW TABLES LIKE ?");
			showTablesPs.setNString(1, tableName.replaceAll("`", ""));
			showTablesRs = showTablesPs.executeQuery();
			return showTablesRs.next();
		} catch (SQLException e) {
			log.error("判断数据库表是否存在，SQL执行异常", e);
			throw e;
		} finally {
			if (null != showTablesPs) {
				try {
					showTablesPs.close();
				} catch (SQLException e) {
					log.error(this.getClass().getCanonicalName() + " 判断数据库表是否存在，关闭查询声明异常(showTablesPs)", e);
				}
			}
			if (null != showTablesRs) {
				try {
					showTablesRs.close();
				} catch (SQLException e) {
					log.error(this.getClass().getCanonicalName() + " 判断数据库表是否存在，关闭结果集异常(showTablesRs)", e);
				}
			}
		}
	}

	/**
	 * 获取数据库表字段集合
	 * @param connection 数据库链接
	 * @param tableName  数据库表名
	 * @return 返回数据库表字段集合
	 * @throws SQLException SQL异常
	 */
	@Override
	protected List<TableColumn> getTableColumns(Connection connection, String tableName) throws SQLException {
		PreparedStatement showTableColumnsPs = null;
		ResultSet showTableColumnsRs = null;

		try {
			showTableColumnsPs = connection.prepareStatement("SHOW COLUMNS FROM " + tableName);
			showTableColumnsRs = showTableColumnsPs.executeQuery();

			List<TableColumn> tableColumnList = new ArrayList<>();
			char leftChar = '(';

			while (showTableColumnsRs.next()) {
				TableColumn tableColumn = new TableColumn();
				tableColumn.setField(showTableColumnsRs.getString("Field"));

				String type = showTableColumnsRs.getString("Type");
				tableColumn.setJdbcType(type.substring(0, 0 >= type.indexOf(leftChar) ? type.length() : type.indexOf(leftChar)));
				tableColumn.setLength(JdbcType.ENUM.equals(tableColumn.getJdbcType()) || 0 >= type.indexOf(leftChar) ? 0 : Integer.valueOf(type.substring(type.indexOf(leftChar) + 1, type.length() - 1).split(",")[0]));

				tableColumn.setNotNull("NO".equals(showTableColumnsRs.getString("Null")));
				tableColumn.setDefaultValue(showTableColumnsRs.getObject("Default"));

				tableColumnList.add(tableColumn);
			}

			return tableColumnList;
		} catch (SQLException e) {
			log.error("获取数据库表字段集合，SQL执行异常", e);
			throw e;
		} finally {
			if (null != showTableColumnsPs) {
				try {
					showTableColumnsPs.close();
				} catch (SQLException e) {
					log.error(this.getClass().getCanonicalName() + " 获取数据库表字段集合，关闭查询声明异常(showTableColumnsPs)", e);
				}
			}
			if (null != showTableColumnsRs) {
				try {
					showTableColumnsRs.close();
				} catch (SQLException e) {
					log.error(this.getClass().getCanonicalName() + " 获取数据库表字段集合，关闭结果集异常(showTableColumnsRs)", e);
				}
			}
		}
	}

	/**
	 * 生成数据库ddl建表SQL
	 * @param generatorInfo 模板生成信息
	 * @return 返回建表SQL
	 */
	@Override
	protected String generatorDdlSql(GeneratorInfo generatorInfo) {
		StringBuilder ddlSql = new StringBuilder();
		this.createTableSql(generatorInfo, ddlSql);
		return ddlSql.toString();
	}

	/**
	 * 建表SQL
	 * @param generatorInfo 模板生成信息
	 * @param ddlSql ddlSql
	 */
	private void createTableSql(GeneratorInfo generatorInfo, StringBuilder ddlSql) {
		Table table = generatorInfo.getTable();

		ddlSql.append("DROP TABLE IF EXISTS ").append(table.getTableName()).append(";\n").append("CREATE TABLE ").append(table.getTableName()).append(" (\n");

		List<Column> primaryKeyList = new ArrayList<>();
		List<Column> indexKeyList = new ArrayList<>();

		createCenterDdlSql(generatorInfo, primaryKeyList, indexKeyList, ddlSql);
		createLastDdlSql(ddlSql, table, primaryKeyList, indexKeyList);
	}

	/**
	 * 是否有默认值
	 */
	private Predicate<Column> hasDefaultValue = column -> null != column.getDefaultVale() && !"".equals(column.getDefaultVale());
	/**
	 * 是否为浮点类型
	 */
	private Predicate<Column> isFloatType = column -> JdbcType.DECIMAL.equals(column.getJdbcType());
	/**
	 * 是否为无长度类型
	 */
	private Predicate<Column> isNoLengthType = column -> JdbcType.TEXT.equals(column.getJdbcType()) || JdbcType.TIMESTAMP.equals(column.getJdbcType());
	/**
	 * 获取小数点位数
	 */
	private Function<Column, String> getDecimalPoint = column -> isFloatType.test(column) ? ", " + column.getDecimalPoint() : "";
	/**
	 * 获取timestamp Null值
	 */
	private Function<Column, String> getTimestamp = column -> JdbcType.TIMESTAMP.equals(column.getJdbcType()) ? " NULL" : "";

	private Function<Column, String> getSpecialDefaultValue = column -> {
		String defaultValue = " DEFAULT NULL";
		if (JdbcType.TIMESTAMP.equals(column.getJdbcType()) && column.getNotNull()) {
			defaultValue = " DEFAULT CURRENT_TIMESTAMP";
		} else if (JdbcType.BIGINT.equals(column.getJdbcType()) || JdbcType.VARCHAR.equals(column.getJdbcType())) {
			defaultValue = "";
		}
		return defaultValue;
	};
	/**
	 * 获取默认值
	 */
	private Function<Column, String> getDefaultValue = column -> hasDefaultValue.test(column) ? " DEFAULT '" + column.getDefaultVale() + "'" : getSpecialDefaultValue.apply(column);


	private Function<Column, Integer> getColumnLength = column -> JdbcType.BIGINT.equals(column.getJdbcType()) ? 20 : column.getLength();

	/**
	 * 创建字段SQL
	 * @param generatorInfo 模板生成信息
	 * @param primaryKeyList 主键列表
	 * @param indexKeyList 索引列表
	 * @param ddlSql ddlSql
	 */
	private void createCenterDdlSql(GeneratorInfo generatorInfo, List<Column> primaryKeyList, List<Column> indexKeyList, StringBuilder ddlSql) {
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
					.append(column.getJdbcType());

			generatorColumnSql(ddlSql, column);

			ddlSql.append(column.getNotNull() ? " NOT NULL" : getTimestamp.apply(column))
					.append(column.getAutoIncrement() ? " AUTO_INCREMENT" : getDefaultValue.apply(column))
					.append("".equals(column.getComment()) ? ",\n" : " COMMENT '" + column.getComment() + "',\n");
		});
	}

	/**
	 * 创建表主键，索引语句
	 * @param ddlSql ddlSql
	 * @param table 表信息
	 * @param primaryKeyList 主键列表
	 * @param indexKeyList 索引列表
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
				.append("".equals(table.getComment()) ? "" : " COMMENT='" + table.getComment() + "'");
	}

	/**
	 * 生成数据库增量表SQL
	 * @param tableColumnList 数据库字段集合
	 * @param generatorInfo 模板生成信息
	 * @return 返回增量表SQL
	 */
	@Override
	protected String generatorAlterSql(List<TableColumn> tableColumnList, GeneratorInfo generatorInfo) {
		Predicate<Column> isNotInTableColumn = column -> tableColumnList.stream().noneMatch(tableColumn -> tableColumn.getField().equals(column.getColumnNameSimple()));
		Predicate<TableColumn> isNotInGeneratorColumn = tableColumn -> generatorInfo.getColumnList().stream().noneMatch(column -> column.getColumnNameSimple().equals(tableColumn.getField()));

		// 需要新增的字段
		List<Column> addColumnList = generatorInfo.getColumnList().stream().filter(isNotInTableColumn).collect(Collectors.toList());

		// 需要删除的字段
		List<TableColumn> deleteColumnList = tableColumnList.stream().filter(isNotInGeneratorColumn).collect(Collectors.toList());

		StringBuilder alterSql = new StringBuilder();

		// 删除字段语句
		if (!deleteColumnList.isEmpty()) {
			alterSql.append("ALTER TABLE ").append(generatorInfo.getTable().getTableName()).append(" ");
			deleteColumnList.forEach(tableColumn -> alterSql.append("DROP COLUMN ").append(tableColumn.getField()).append(", "));

			alterSql.deleteCharAt(alterSql.length() - 1);
			alterSql.deleteCharAt(alterSql.length() - 1);
			alterSql.append(";");
		}

		// 新增字段语句
		if (!addColumnList.isEmpty()) {
			alterSql.append("ALTER TABLE ").append(generatorInfo.getTable().getTableName()).append(" ");
			addColumnList.forEach(column -> {
				alterSql.append("ADD COLUMN ")
					.append(column.getColumnName())
					.append(" ")
					.append(column.getJdbcType());

				generatorColumnSql(alterSql, column);

				alterSql.append(column.getNotNull() ? " NOT NULL" : getTimestamp.apply(column))
					.append(getDefaultValue.apply(column))
					.append("".equals(column.getComment()) ? ", " : " COMMENT '" + column.getComment() + "', ");
			});

			alterSql.deleteCharAt(alterSql.length() - 1);
			alterSql.deleteCharAt(alterSql.length() - 1);
		}

		return alterSql.toString();
	}

	/**
	 * 创建字段SQL
	 * @param sql sql语句Builder
	 * @param column 字段
	 */
	private void generatorColumnSql(StringBuilder sql, Column column) {
		if (JdbcType.ENUM.equals(column.getJdbcType())) {
			sql.append("(");
			for (int i = 0, size = column.getEnumValues().length; i < size; i++) {
				if (0 != i) {
					sql.append(", ");
				}
				sql.append("'").append(column.getEnumValues()[i]).append("'");
			}
			sql.append(")");
		} else {
			sql.append(isNoLengthType.test(column) ? "" : "(").append(isNoLengthType.test(column) ? "" : getColumnLength.apply(column))
					.append(isNoLengthType.test(column) ? "" : getDecimalPoint.apply(column)).append(isNoLengthType.test(column) ? "" : ")");
		}
	}

	/**
	 * 执行SQL
	 * @param connection 数据库链接
	 * @param executeSql SQL语句
	 * @throws SQLException SQL异常
	 */
	@Override
	protected void executeSql(Connection connection, String executeSql) throws SQLException {
		log.info("执行SQL \n{};",  executeSql);
		System.out.println("执行SQL \n " + executeSql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(executeSql)) {
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			log.error("执行SQL，SQL执行异常", e);
			throw e;
		}
	}
}
