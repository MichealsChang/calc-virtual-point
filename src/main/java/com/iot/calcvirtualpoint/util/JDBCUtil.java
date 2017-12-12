package com.iot.calcvirtualpoint.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

	private static JDBCUtil instance = null;

	private static String url = null;
	private static String username = null;
	private static String password = null;

	private static Properties p = null;

	private JDBCUtil() {

	}

	static {
		p = new Properties();
		InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("conf/mysql.properties");
		try {
			p.load(in);
			Class.forName("com.mysql.jdbc.Driver");
			url = p.getProperty("config.mysql.master.url");
			username = p.getProperty("config.mysql.master.username");
			password = p.getProperty("config.mysql.master.password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JDBCUtil getInstance() {
		if (instance == null) {
			synchronized (JDBCUtil.class) {
				if (instance == null) {
					instance = new JDBCUtil();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
