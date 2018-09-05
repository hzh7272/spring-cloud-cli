package com.example.commons.core.generator.core;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.Writer;

/**
 * 基础MyBatis生成工具
 * @author hzh 2018/9/5 下午9:13
 */
public class BasicMyBatisGenerator extends AbstractMyBatisGenerator {

	public BasicMyBatisGenerator(GeneratorInfo generatorInfo, GeneratorConfig generatorConfig) {
		this.initGeneratorInfo(generatorInfo, generatorConfig);
	}

	/**
	 * 生成XML
	 *
	 * @param velocityEngine  Velocity模板引擎
	 * @param velocityContext Velocity模板上下文
	 */
	@Override
	protected void generatorXml(VelocityEngine velocityEngine, VelocityContext velocityContext) {
		Template template = velocityEngine.getTemplate("mybatisXml.vm", "UTF-8");
		try (Writer writer = new FileWriterWithEncoding((String) velocityContext.get("xmlPath"), "UTF-8")) {
//		try (Writer writer = new FileWriterWithEncoding("/Users/hzh/workspace/generater/test.xml", "UTF-8")) {
			template.merge(velocityContext, writer);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成Mapper接口
	 *
	 * @param velocityEngine  Velocity模板引擎
	 * @param velocityContext Velocity模板上下文
	 */
	@Override
	protected void generatorMapper(VelocityEngine velocityEngine, VelocityContext velocityContext) {
		Template template = velocityEngine.getTemplate("mybatisMapper.vm", "UTF-8");
		try (Writer writer = new FileWriterWithEncoding((String) velocityContext.get("mapperPath"), "UTF-8")) {
			template.merge(velocityContext, writer);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
