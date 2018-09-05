package com.example.commons.core.base;

import com.example.commons.core.generator.annotation.TableColumn;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * 持久化数据模型基类
 * @author hzh 2018/9/3 17:59
 */
@Data
public class BasicModel implements Serializable {

    @TableColumn(comment = "创建时间", notNull = true)
    private Instant createTime;
    @TableColumn(comment = "创建人", notNull = true)
    private Long createUserId;
    @TableColumn(comment = "修改时间")
    private Instant modifyTime;
    @TableColumn(comment = "修改人")
    private Long modifyUserId;
    @TableColumn(length = 1, defaultValue = "1", comment = "删除状态（1：有效，2：删除）")
    private Integer deleteStatus;
    @TableColumn(length = 1, defaultValue = "1", comment = "状态（1：有效，0：无效）")
    private Integer status;
}
