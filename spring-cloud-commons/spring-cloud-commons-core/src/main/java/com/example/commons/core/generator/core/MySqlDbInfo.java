package com.example.commons.core.generator.core;

import lombok.Data;

/**
 * 数据库信息
 * @version 1.0.0
 * @author hzh 2018/8/26 下午8:45
 */
@Data
public class MySqlDbInfo {

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String driver;
	private String database;

	private String url;


	public MySqlDbInfo(String database, String password) {
		this.database = database;
		this.url = "jdbc:mysql:///" + this.database + "?characterEncoding=utf8&useSSL=false";
		this.username = "root";
		this.password = password;
		this.driver = "com.mysql.jdbc.Driver";
	}

	public MySqlDbInfo(String host, int port, String username, String password, String driver, String database) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.driver = driver;
		this.database = database;

		this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?characterEncoding=utf8&useSSL=false";
	}
}
