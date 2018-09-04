package com.example.commons.core.base;

import lombok.Data;

import java.time.Instant;

/**
 * 持久化数据模型基类
 * @author hzh 2018/9/3 17:59
 */
@Data
public class BasicModel {

    private Instant createTime;
    private Long createUserId;
    private Instant modifyTime;
    private Long modifyUserId;
    private Integer deleteStatus;
    private Integer status;
}
