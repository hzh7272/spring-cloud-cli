package com.example.commons.core.generator;

import com.example.commons.core.generator.core.CodeGenerator;
import com.example.commons.core.generator.core.GeneratorConfig;

/**
 * @author hzh 2018/9/5 16:51
 */
public class Test {

    public static void main(String[] args) {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        generatorConfig.setGeneratorTable(true);
        new CodeGenerator().generateCode(TestDemo.class, generatorConfig, null, "", "");
    }
}
