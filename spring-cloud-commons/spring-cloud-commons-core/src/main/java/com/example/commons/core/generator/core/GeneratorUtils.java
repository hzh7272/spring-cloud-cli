package com.example.commons.core.generator.core;

/**
 * 生成器工具
 * @author hzh 2018/8/26 下午11:23
 */
public class GeneratorUtils {

	private GeneratorUtils() {}

	/**
	 * Class作为变量时的句柄名称
	 */
	private static String getClassHandleName(String simpleName) {
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
	}

	public static String formatName(String name) {
		name = getClassHandleName(name);

		StringBuilder columnName = new StringBuilder();
		for (int i = 0, size = name.length(); i < size; i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				columnName.append('_');
				columnName.append(Character.toLowerCase(c));
			} else {
				columnName.append(c);
			}
		}

		return "`" + columnName.toString() + "`";
	}
}
