package com.example.consumer.user.dto;

import lombok.Data;

/**
 * 系统用户数据传输模型
 * @author hzh 2018/9/13 10:29
 */
@Data
public class SystemUser {

    private Long id;
    private String account;
    private String mobile;
    private String username;
    private String avatarUrl;
    private Integer superAdmin;
}
