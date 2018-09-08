package com.example.producer.user;

import com.example.commons.core.generator.core.CodeGenerator;
import com.example.commons.core.generator.core.GeneratorConfig;
import com.example.commons.core.generator.core.MySqlDbInfo;
import com.example.producer.user.model.*;

/**
 * 代码生成器
 * @author hzh 2018/9/8 下午11:38
 */
public class UserCodeGenerator {

	public static void main(String[] args) {
		MySqlDbInfo mySqlDbInfo = new MySqlDbInfo("localhost", 3306, "spring-cloud", "springCloud@2018", "com.mysql.jdbc.Driver", "spring-cloud-user");

		String projectPath = "/Users/hzh/workspace/project/spring-cloud-cli/spring-cloud-producer/spring-cloud-producer-user";
		String xmlPath = projectPath + "/src/main/resources/mapper/";
		String mapperPath = projectPath + "/src/main/java/com/example/producer/user/dao/";
		String packageName = "com.example.producer.user.dao";

		GeneratorConfig generatorConfig = new GeneratorConfig(xmlPath, mapperPath, packageName, "hzh");
		generatorConfig.setGeneratorTable(true);
		generatorConfig.setGeneratorMapperXml(true);
		generatorConfig.setGeneratorMapper(true);

		CodeGenerator codeGenerator = new CodeGenerator(generatorConfig, mySqlDbInfo);

//		codeGenerator.generateCode(User.class);
//		codeGenerator.generateCode(UserRole.class);
//		codeGenerator.generateCode(Role.class);
//		codeGenerator.generateCode(RolePermission.class);
		codeGenerator.generateCode(Permission.class);
	}
}
