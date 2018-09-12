package com.exmaple.producer.user.controller;

import com.example.producer.user.UserProducerApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 配置文件加密测试
 * @author hzh 2018/9/12 14:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserProducerApplication.class)
public class YamlEncryptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void whenEncryptSuccess() {
        String password = "springCloud@2018";
        System.out.println(stringEncryptor.encrypt(password));
    }
}
