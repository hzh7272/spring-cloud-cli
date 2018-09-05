package com.example.commons.core.generator.core;

import lombok.Data;

/**
 * 生成代码配置
 * @version 1.0.0
 * @author hzh 2018/8/26 下午8:59
 */
@Data
public class GeneratorConfig {

	private boolean generatorAll;
	private boolean generatorTable;
	private boolean generatorMapper;
	private boolean generatorMapperXml;
	private boolean generatorService;
	private boolean generatorController;
	private boolean generatorStaticHtmlAndJs;

	private String xmlPath;
	private String mapperPath;
	private String mapperPackage;
	private String author;

	public GeneratorConfig(String xmlPath, String mapperPath, String mapperPackage, String author) {
		this.generatorAll = false;
		this.generatorTable = false;
		this.generatorMapper = false;
		this.generatorMapperXml = false;
		this.generatorService = false;
		this.generatorController = false;
		this.generatorStaticHtmlAndJs = false;

		this.xmlPath = xmlPath;
		this.mapperPath = mapperPath;
		this.mapperPackage = mapperPackage;
		this.author = author;
	}
}
