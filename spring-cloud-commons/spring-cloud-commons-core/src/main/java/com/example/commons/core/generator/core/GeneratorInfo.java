package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Model;
import com.example.commons.core.generator.bean.Table;
import lombok.Data;

import java.util.List;

/**
 * 生成代码数据信息
 * @author hzh 2018/8/26 下午10:16
 */
@Data
public class GeneratorInfo {

	private Table table;
	private List<Column> columnList;
	private Model model;
	private String myBatisXmlPath;
	private String myBatisMapperPath;
	private String myBatisMapperPackage;
	private String author;
	private String time;

	public GeneratorInfo(Table table, List<Column> columnList, Model model) {
		this.table = table;
		this.columnList = columnList;
		this.model = model;
	}
}
