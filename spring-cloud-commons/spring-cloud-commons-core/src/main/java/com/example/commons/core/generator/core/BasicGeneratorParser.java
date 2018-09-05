package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import com.example.commons.core.generator.bean.Column;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.commons.core.generator.core.GeneratorUtils.formatName;

/**
 * 数据库模型分析器
 * @version 1.0.0
 * @author hzh 2018/8/26 下午10:51
 */
public class BasicGeneratorParser implements GeneratorParser {

	private Class<?> clazz;

	/**
	 * 初始化
	 * @param clazz 实体类型型
	 * @return 返回当前示例
	 */
	@Override
	public BasicGeneratorParser init(Class clazz) {
		this.clazz = clazz;
		return this;
	}

	/**
	 * 分析
	 */
	@Override
	public GeneratorInfo parse() {
		this.checkClass();
		// 解析表数据
		Table table = this.parseTable();
		List<Column> columnList = this.parseColumns();
		return new GeneratorInfo(table, columnList);
	}

	/**
	 * 类类型检查
	 */
	private void checkClass() {
		if (!Serializable.class.isAssignableFrom(this.clazz)) {
			throw new RuntimeException(this.clazz.getSimpleName() + " 需要实现Serializable接口");
		}
		if (null == this.clazz.getAnnotation(com.example.commons.core.generator.annotation.Table.class)) {
			throw new RuntimeException(this.clazz.getSimpleName() + " 需要添加@Table注解");
		}
	}

	/**
	 * 解析表数据
	 * @return 返回表数据
	 */
	private Table parseTable() {
		Table table = new Table();
		Optional.ofNullable(this.clazz.getAnnotation(com.example.commons.core.generator.annotation.Table.class)).ifPresent(annotation -> {
			table.setTableName(formatName(Optional.ofNullable(annotation.name()).orElse(this.clazz.getSimpleName())));
			table.setComment(annotation.comment());
			table.setCharset(annotation.charset());
			table.setEngine(annotation.engine());
			table.setUpdateAble(annotation.updateAble());
		});
		return table;
	}

	/**
	 * 解析数据库字段
	 * @return 返回数据库字段集合
	 */
	private List<Column> parseColumns() {
		return Arrays.stream(this.clazz.getDeclaredFields()).map(field -> {
			TableColumn tableColumn = field.getAnnotation(TableColumn.class);
			Column column = new Column();
			column.setPrimaryKey(tableColumn.primaryKey());
			column.setAutoIncrement(tableColumn.autoIncrease());
			column.setColumnName(formatName(field.getName()));
			column.setJdbcType(jdbcType(field.getType(), tableColumn));
			column.setLength(tableColumn.length());
			column.setNotNull(tableColumn.notNull());
			column.setDefaultVale(tableColumn.defaultValue());
			column.setIndexKey(tableColumn.index());
			column.setUniqueKey(tableColumn.uniqueKey());
			column.setComment(tableColumn.comment());
			column.setDecimalPoint(tableColumn.decimalPoint());
			return column;
		}).collect(Collectors.toList());
	}

	/**
	 * 获取字段数据库jdbc类型
	 * @param filedType 字段类型
	 * @param tableColumn 字段上注解
	 * @return 数据库jdbc类型
	 */
	private String jdbcType(Class filedType, TableColumn tableColumn) {
		String jdbcType = "";
		if (String.class == filedType) {
			jdbcType = tableColumn.bigText() ? "TEXT" : "VARCHAR";
		} else if (Integer.class == filedType) {
			jdbcType = 1 == tableColumn.length() ? "TINYINT" : "INT";
		} else if (Long.class == filedType) {
			jdbcType = "BIGINT";
		} else if (Date.class == filedType || Instant.class == filedType || ZonedDateTime.class == filedType || LocalDateTime.class == filedType) {
			jdbcType = "TIMESTAMP";
		} else if (Float.class == filedType || Double.class == filedType || BigDecimal.class == filedType) {
			jdbcType = "DECIMAL";
		}
		return jdbcType;
	}
}
