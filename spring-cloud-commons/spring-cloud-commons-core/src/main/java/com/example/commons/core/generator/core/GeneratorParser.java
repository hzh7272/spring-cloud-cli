package com.example.commons.core.generator.core;

/**
 * 实体分析器
 * @author hzh 2018/8/26 下午10:53
 */
public interface GeneratorParser {

	/**
	 * 初始化
	 * @param clazz 实体类型型
	 * @return 返回当前示例
	 */
	GeneratorParser init(Class clazz);

	/**
	 * 分析
	 * @return 实体分析数据
	 */
	GeneratorInfo parse();
}
