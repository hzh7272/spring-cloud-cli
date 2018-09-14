package com.example.tools.code;

import java.math.BigDecimal;

/**
 * 36进制编码生产器
 * @author hzh 2018/9/14 17:17
 */
public class Radix36CodeGenerator {

    private Radix36CodeGenerator() {}

    /**
     * 进制
     */
    private static final BigDecimal RADIX = BigDecimal.valueOf(36);

    /**
     * 自定义36进制编码
     */
    private static final String[] CODES = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 生成编码
     * @param generatorCode 编码生产数字字符串
     * @return 返回生成的编码
     */
    public static synchronized String generatorCode(String generatorCode) {
        BigDecimal bigDecimal = new BigDecimal(generatorCode);
        StringBuilder code = new StringBuilder();

        do{
            if (RADIX.compareTo(bigDecimal) > 0) {
                break;
            }
            code.append(CODES[bigDecimal.remainder(RADIX).intValue()]);
            bigDecimal = bigDecimal.divide(RADIX, BigDecimal.ROUND_DOWN);
        } while (RADIX.compareTo(bigDecimal) < 1);

        return code.toString();
    }
}
