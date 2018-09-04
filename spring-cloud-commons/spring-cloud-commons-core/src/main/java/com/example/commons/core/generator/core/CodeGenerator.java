package com.example.commons.core.generator.core;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成器
 * @author hzh 2018/8/26 下午8:44
 * @version 1.0.0
 */
@Slf4j
public class CodeGenerator {

	public void generateCode(Class<?> pojoClass, GeneratorConfig generatorConfig, MySqlDbInfo mySqlDbInfo, String bPath, String fPath) {
		GeneratorInfo generatorInfo = new BasicGeneratorParser().init(pojoClass).parse();

		if (generatorConfig.isGeneratorAll()) {
			// 全部生成
		} else {
			// 指定生成
			if (generatorConfig.isGeneratorTable()) {
				// 生成数据库表
				BasicMySqlDdlGenerator basicMySqlDdlGenerator = new BasicMySqlDdlGenerator(mySqlDbInfo, generatorInfo);
				basicMySqlDdlGenerator.generatorTable();
			}
		}

	}
}
