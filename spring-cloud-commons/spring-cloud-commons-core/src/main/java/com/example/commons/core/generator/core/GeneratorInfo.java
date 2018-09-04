package com.example.commons.core.generator.core;

import com.example.commons.core.generator.bean.Column;
import com.example.commons.core.generator.bean.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 生成代码数据信息
 * @author hzh 2018/8/26 下午10:16
 */
@Data
@AllArgsConstructor
public class GeneratorInfo {

	private Table table;
	private List<Column> columnList;
}
