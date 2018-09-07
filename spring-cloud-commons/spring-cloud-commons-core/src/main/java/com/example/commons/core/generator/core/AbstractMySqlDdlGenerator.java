package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Table;
import com.example.commons.core.generator.bean.TableColumn;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库表
 * @version 1.0.0
 * @author hzh 2018/8/26 下午9:16
 */
@Slf4j
public abstract class AbstractMySqlDdlGenerator {

	private MySqlDbInfo mySqlDbInfo;
	private GeneratorInfo generatorInfo;

	public AbstractMySqlDdlGenerator(MySqlDbInfo mySqlDbInfo, GeneratorInfo generatorInfo) {
		this.mySqlDbInfo = mySqlDbInfo;
		this.generatorInfo = generatorInfo;
	}

	/**
	 * 判断数据库表是否存在
	 * @param connection 数据库链接
	 * @param tableName 数据库表名
	 * @return 返回true则存在，false则不存在
	 * @throws SQLException SQL异常
	 */
	protected abstract boolean isTableExits(Connection connection, String tableName) throws SQLException;

	/**
	 * 获取数据库表字段集合
	 * @param connection 数据库链接
	 * @param tableName 数据库表名
	 * @return 返回数据库表字段集合
	 * @throws SQLException SQL异常
	 */
	protected abstract List<TableColumn> getTableColumns(Connection connection, String tableName) throws SQLException;

	/**
	 * 生成数据库ddl建表SQL
	 * @param generatorInfo 模板生成信息
	 * @return 返回建表SQL
	 */
	protected abstract String generatorDdlSql(GeneratorInfo generatorInfo);

	/**
	 * 生成数据库增量表SQL
	 * @param tableColumnList 数据库字段集合
	 * @param generatorInfo 模板生成信息
	 * @return 返回增量表SQL
	 */
	protected abstract String generatorAlterSql(List<TableColumn> tableColumnList, GeneratorInfo generatorInfo);

	/**
	 * 执行SQL
	 * @param connection 数据库链接
	 * @param executeSql SQL语句
	 * @throws SQLException SQL异常
	 */
	protected abstract void executeSql(Connection connection, String executeSql) throws SQLException;

	/**
	 * 生成数据库表
	 */
	public final void generatorTable() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(mySqlDbInfo.getUrl(), mySqlDbInfo.getUsername(), mySqlDbInfo.getPassword());
			connection.setAutoCommit(false);

			Table table = generatorInfo.getTable();

			if (!this.isTableExits(connection, this.generatorInfo.getTable().getTableName()) || table.getDropIfExits()) {
				// 表不存在，获取需要删除表重建
				String ddlSql = this.generatorDdlSql(this.generatorInfo);
				for (String sql : ddlSql.split(";")) {
					this.executeSql(connection, sql);
				}
			} else {
				// 更新表结构
				if (table.getUpdateAble()) {
					List<TableColumn> tableColumnList = this.getTableColumns(connection, this.generatorInfo.getTable().getTableName());
					String alertSql = this.generatorAlterSql(tableColumnList, this.generatorInfo);
					for (String sql : alertSql.split(";")) {
						this.executeSql(connection, sql);
					}
				}
			}

			// 提交事务
			connection.commit();
		} catch (SQLException e) {
			// 回滚事务
			try {
				if (null != connection) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				log.error(this.getClass().getCanonicalName() + " 生成数据库表，事务回滚异常", e1);
			}
			log.error(this.getClass().getCanonicalName() + " 生成数据库表，SQL执行异常", e);
		} finally {
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(this.getClass().getCanonicalName() + " 生成数据库表，关闭数据库链接异常", e);
				}
			}
		}
	}
}
