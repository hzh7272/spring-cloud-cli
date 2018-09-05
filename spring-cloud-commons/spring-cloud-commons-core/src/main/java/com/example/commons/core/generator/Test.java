package com.example.commons.core.generator;

import com.example.commons.core.generator.core.CodeGenerator;
import com.example.commons.core.generator.core.GeneratorConfig;
import com.example.commons.core.generator.core.MySqlDbInfo;

/**
 * @author hzh 2018/9/5 16:51
 */
public class Test {

    public static void main(String[] args) {
        MySqlDbInfo mySqlDbInfo = new MySqlDbInfo("localhost", 3306, "spring-cloud", "springCloud@2018", "com.mysql.jdbc.Driver", "spring-cloud-user");

        GeneratorConfig generatorConfig = new GeneratorConfig("/Users/hzh/workspace/generator/", "/Users/hzh/workspace/generator/", "com.example.dao", "hzh");
        generatorConfig.setGeneratorTable(true);
        generatorConfig.setGeneratorMapperXml(true);
        generatorConfig.setGeneratorMapper(true);
        new CodeGenerator().generateCode(TestDemo.class, generatorConfig, mySqlDbInfo);
    }
}
