package com.example.commons.core.generator.core;

/**
 * 生成器工具
 * @author hzh 2018/8/26 下午11:23
 */
public class GeneratorUtils {

	/**
	 * Class作为变量时的句柄名称
	 */
	public static String getClassHandleName(String simpleName) {
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
	}

	public static String formatName(String name) {
		name = getClassHandleName(name);

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0, size = name.length(); i < size; i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				stringBuffer.append('_');
				stringBuffer.append(Character.toLowerCase(c));
			} else {
				stringBuffer.append(c);
			}
		}

		return "`" + stringBuffer.toString() + "`";
	}
}
