package com.example.commons.core.generator;

import com.example.commons.core.base.BasicModel;
import com.example.commons.core.generator.annotation.Table;
import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author hzh 2018/9/5 16:22
 */
@Data
@Table(name = "test_demo", comment = "测试")
public class TestDemo extends BasicModel {

    @TableColumn(primaryKey = true, autoIncrease = true, comment = "逐渐", notNull = true)
    private Long id;
    @TableColumn(length = 32, comment = "名称")
    private String name;
    @TableColumn(length = 1, comment = "状态")
    private Integer state;
    @TableColumn(length = 10, decimalPoint = 2, comment = "价格")
    private BigDecimal price;
    @TableColumn(comment = "用户ID", defaultValue = "1", notNull = true, index = true, uniqueKey = true)
    private Long memberId;
    @TableColumn(bigText = true, comment = "内容")
    private String content;
    @TableColumn(comment = "tttt")
    private Instant instant;

}
