package com.exmaple.tools.string;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * 字符串工具
 * @author hzh 2018/8/2 13:21
 * @since 1.8
 */
public class StringUtils {

    /**
     * 匹配类型
     */
    public enum Match {
        /**
         * 部分匹配
         */
        OR,
        /**
         * 全部匹配
         */
        AND;
    }

    /**
     * 字符串判空断言
     */
    private static Predicate<String> isEmpty = string -> null == string || "null".equals(string) || "".equals(string);

    /**
     * 判断字符串是否为空
     * @param string 字符串数据
     * @return 返回true则为空，false则非空
     */
    public static boolean isEmpty(String string) {
        return isEmpty.test(string);
    }

    /**
     * 判断String集合是否为空
     * @param match 匹配类型
     * @param strings 可变字符串参数
     * @return 返回true则为空，false则非空
     */
    private static boolean isEmpty(Match match, String... strings) {
        Predicate<String[]> allEmpty = stringArray -> Arrays.stream(stringArray).allMatch(isEmpty);
        Predicate<String[]> anyEmpty = stringArray -> Arrays.stream(stringArray).anyMatch(isEmpty);
        switch (match) {
            case AND:
                return allEmpty.test(strings);
            case OR:
                return anyEmpty.test(strings);
            default:
                return allEmpty.test(strings);
        }
    }

    /**
     * 判断String集合是否全为空
     * @param strings 字符串数据
     * @return 返回true则为空，false则非空
     */
    public static boolean isAllEmpty(String... strings) {
        return isEmpty(Match.AND, strings);
    }

    /**
     * 判断String集合是否有一个为空
     * @param strings 可变字符串参数
     * @return 返回true则为空，false则非空
     */
    public static boolean isAnyEmpty(String... strings) {
        return isEmpty(Match.OR, strings);
    }
}