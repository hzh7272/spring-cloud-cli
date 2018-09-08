package com.example.commons.core.base;

import com.example.commons.core.generator.annotation.TableColumn;
import com.example.commons.global.enumm.DeleteStatus;
import com.example.commons.global.enumm.GlobalStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * 持久化数据模型基类
 * @version 1.0.0
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
    @TableColumn(defaultValue = "VALID", comment = "删除状态")
    private DeleteStatus deleteStatus;
    @TableColumn(defaultValue = "VALID", comment = "状态")
    private GlobalStatus status;
}
