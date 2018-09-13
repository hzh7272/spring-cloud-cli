package com.example.commons.core.generator.core;

import com.example.commons.core.base.BasicModel;
import com.example.commons.core.generator.annotation.TableColumn;
import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Model;
import com.example.commons.core.generator.bean.Table;
import com.example.commons.core.generator.support.JdbcType;

import java.io.Serializable;
import java.lang.reflect.Field;
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
import static com.example.commons.core.generator.core.GeneratorUtils.formatNameSimple;

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
		// 子类字段
		List<Column> columnList = this.parseColumns(this.clazz.getDeclaredFields());
		// 基类字段
		columnList.addAll(this.parseColumns(this.clazz.getSuperclass().getDeclaredFields()));
		Model model = this.parsePojo();
		return new GeneratorInfo(table, columnList, model);
	}

	/**
	 * 类类型检查
	 */
	private void checkClass() {
		if (!Serializable.class.isAssignableFrom(this.clazz)) {
			throw new RuntimeException(this.clazz.getSimpleName() + " 需要实现Serializable接口");
		}
		if (!BasicModel.class.isAssignableFrom(this.clazz)) {
			throw new RuntimeException(this.clazz.getSimpleName() + " 需要继承BasicModel基类");
		}
		if (null == this.clazz.getAnnotation(com.example.commons.core.generator.annotation.Table.class)) {
			throw new RuntimeException(this.clazz.getSimpleName() + " 需要添加@Table注解");
		}
	}

	/**
	 * 解析实体
	 * @return 返回实体信息
	 */
	private Model parsePojo() {
		Model model = new Model();
		model.setPackageName(this.clazz.getPackage().getName());
		model.setClassName(this.clazz.getSimpleName());
		model.setClassFullName(this.clazz.getName());
		return model;
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
			table.setDropIfExits(annotation.dropIfExist());
		});
		return table;
	}

	/**
	 * 解析数据库字段
	 * @return 返回数据库字段集合
	 */
	private List<Column> parseColumns(Field[] fields) {
		return Arrays.stream(fields).map(field -> {
			TableColumn tableColumn = field.getAnnotation(TableColumn.class);

			Column column = new Column();
			column.setPrimaryKey(tableColumn.primaryKey());
			column.setAutoIncrement(tableColumn.autoIncrease());
			column.setColumnName(formatName(field.getName()));
			column.setColumnNameSimple(formatNameSimple(field.getName()));
			column.setJdbcType(jdbcType(field.getType(), tableColumn));
			column.setLength(tableColumn.length());
			column.setNotNull(tableColumn.notNull());
			column.setDefaultVale(tableColumn.defaultValue());
			column.setIndexKey(tableColumn.index());
			column.setUniqueKey(tableColumn.uniqueKey());
			column.setComment(tableColumn.comment());
			column.setDecimalPoint(tableColumn.decimalPoint());
			column.setFieldName(field.getName());

			if (field.getType().isEnum()) {
				try {
					column.setEnumValues(field.getType().getEnumConstants());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

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
			jdbcType = tableColumn.bigText() ? JdbcType.TEXT : JdbcType.VARCHAR;
		} else if (Integer.class == filedType) {
			jdbcType = 1 == tableColumn.length() ? JdbcType.TINYINT : JdbcType.INT;
		} else if (Long.class == filedType) {
			jdbcType = JdbcType.BIGINT;
		} else if (Date.class == filedType || Instant.class == filedType || ZonedDateTime.class == filedType || LocalDateTime.class == filedType) {
			jdbcType = JdbcType.TIMESTAMP;
		} else if (Float.class == filedType || Double.class == filedType || BigDecimal.class == filedType) {
			jdbcType = JdbcType.DECIMAL;
		} else if (filedType.isEnum()) {
			jdbcType = JdbcType.ENUM;
		}
		return jdbcType;
	}
}
