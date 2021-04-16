package com.jasonqjc.version1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DBUtil {
  private static final String DRIVER;
  private static final String URL;
  private static final String USERNAME;
  private static final String PASSWORD;

  static {
    Properties conf = PropsUtil.loadProps("config.properties");
    DRIVER = conf.getProperty("jdbc.driver");
    URL = conf.getProperty("jdbc.url");
    USERNAME = conf.getProperty("jdbc.username");
    PASSWORD = conf.getProperty("jdbc.password");
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      log.error("can not load jdbc driver", e);
    }
  }

  public static Connection getConnection() {
    try {
      Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      return conn;
    } catch (SQLException e) {
      e.printStackTrace();
      log.error("get Connection fail!", e);
    }
    return null;
  }

}
