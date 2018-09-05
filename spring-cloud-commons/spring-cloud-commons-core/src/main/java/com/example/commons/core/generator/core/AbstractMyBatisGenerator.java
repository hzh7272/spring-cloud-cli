package com.example.commons.core.generator.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.util.Properties;

/**
 * MyBatis生成器
 * @author hzh 2018/9/5 下午9:10
 */
@Slf4j
public abstract class AbstractMyBatisGenerator {

	private GeneratorInfo generatorInfo;
	private GeneratorConfig generatorConfig;

	/**
	 * 初始化生成信息
	 * @param generatorInfo 模板生成信息
	 * @param generatorInfo 模板生成配置
	 */
	protected void initGeneratorInfo(GeneratorInfo generatorInfo, GeneratorConfig generatorConfig) {
		this.generatorInfo = generatorInfo;
		this.generatorConfig = generatorConfig;
	}

	/**
	 * 生成XML
	 * @param velocityEngine Velocity模板引擎
	 * @param velocityContext Velocity模板上下文
	 */
	protected abstract void generatorXml(VelocityEngine velocityEngine, VelocityContext velocityContext);

	/**
	 * 生成Mapper接口
	 * @param velocityEngine Velocity模板引擎
	 * @param velocityContext Velocity模板上下文
	 */
	protected abstract void generatorMapper(VelocityEngine velocityEngine, VelocityContext velocityContext);

	/**
	 * 生成MyBatis Xml和Mapper
	 */
	public final void generator() {
		Properties properties = new Properties();
		String key = "file.resource.loader.class";
		String value = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";
		properties.put(key, value);

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init(properties);

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("generatorInfo", this.generatorInfo);
		velocityContext.put("mapperPath", this.generatorInfo.getMyBatisMapperPath());
		velocityContext.put("xmlPath", this.generatorInfo.getMyBatisXmlPath());

		File xmlFolder = new File(this.generatorConfig.getXmlPath());
		if (!xmlFolder.exists()) {
			log.info("xml文件生成路径 {} 不存在，创建文件夹：{}", this.generatorConfig.getXmlPath(), xmlFolder.mkdir());
		}

		File mapperFolder = new File(this.generatorConfig.getMapperPath());
		if (!mapperFolder.exists()) {
			log.info("mapper文件生成路径 {} 不存在，创建文件夹：{}", this.generatorConfig.getXmlPath(), mapperFolder.mkdir());
		}

		if (generatorConfig.isGeneratorMapperXml()) {
			this.generatorXml(velocityEngine, velocityContext);
		}
		if (generatorConfig.isGeneratorMapper()) {
			this.generatorMapper(velocityEngine, velocityContext);
		}
	}
}
