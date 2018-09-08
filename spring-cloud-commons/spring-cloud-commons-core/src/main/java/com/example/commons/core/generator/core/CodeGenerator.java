package com.example.commons.core.generator.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 代码生成器
 * @author hzh 2018/8/26 下午8:44
 * @version 1.0.0
 */
@Slf4j
@Data
public class CodeGenerator {

	private BasicMySqlDdlGenerator basicMySqlDdlGenerator;
	private BasicMyBatisGenerator basicMyBatisGenerator;

	private GeneratorConfig generatorConfig;
	private MySqlDbInfo mySqlDbInfo;

	public CodeGenerator(GeneratorConfig generatorConfig, MySqlDbInfo mySqlDbInfo) {
		this.generatorConfig = generatorConfig;
		this.mySqlDbInfo = mySqlDbInfo;
	}

	public void generateCode(Class<?> pojoClass) {
		GeneratorInfo generatorInfo = new BasicGeneratorParser().init(pojoClass).parse();
		generatorInfo.setAuthor(generatorConfig.getAuthor());
		generatorInfo.setTime(Instant.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		generatorInfo.setMyBatisXmlPath(generatorConfig.getXmlPath() + generatorInfo.getModel().getClassName() + "Mapper.xml");
		generatorInfo.setMyBatisMapperPath(generatorConfig.getMapperPath() + generatorInfo.getModel().getClassName() + "Mapper.java");
		generatorInfo.setMyBatisMapperPackage(generatorConfig.getMapperPackage());

		if (generatorConfig.isGeneratorAll()) {
			// 全部生成
			generatorTable(mySqlDbInfo, generatorInfo);
			generatorMyBatisXmlAndMapper(generatorConfig, generatorInfo);
		} else {
			// 指定生成
			if (generatorConfig.isGeneratorTable()) {
				// 生成数据库表
				generatorTable(mySqlDbInfo, generatorInfo);
			}
			if (generatorConfig.isGeneratorMapperXml() || generatorConfig.isGeneratorMapper()) {
				// 生成MyBatis Xml 和 Mapper
				generatorMyBatisXmlAndMapper(generatorConfig, generatorInfo);
			}
		}
	}

	/**
	 * 生成数据库表
	 * @param mySqlDbInfo 数据库信息
	 * @param generatorInfo 模板生成信息
	 */
	private void generatorTable(MySqlDbInfo mySqlDbInfo, GeneratorInfo generatorInfo) {
		this.basicMySqlDdlGenerator = Optional.ofNullable(this.basicMySqlDdlGenerator).orElse(new BasicMySqlDdlGenerator(mySqlDbInfo, generatorInfo));
		this.basicMySqlDdlGenerator.generatorTable();
	}

	/**
	 * 生成MyBatis Xml 和 Mapper
	 * @param generatorConfig 模板生成配置
	 * @param generatorInfo 模板生成信息
	 */
	private void generatorMyBatisXmlAndMapper(GeneratorConfig generatorConfig, GeneratorInfo generatorInfo) {
		this.basicMyBatisGenerator = Optional.ofNullable(this.basicMyBatisGenerator).orElse(new BasicMyBatisGenerator(generatorInfo, generatorConfig));
		this.basicMyBatisGenerator.generator();
	}
}
