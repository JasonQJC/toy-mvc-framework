package com.jasonqjc.version1.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.jasonqjc.version1.model.Coffee;
import com.jasonqjc.version1.util.PropsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoffeeService {

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

  public List<Coffee> getCoffeeList() {
    String sql = "select * from coffee";
    try (
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        ResultSet rs = prepareStatement.executeQuery();) {
      List<Coffee> list = Lists.newArrayList();
      while (rs.next()) {
        Coffee coffee = Coffee.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .createBy(rs.getString("createBy"))
            .price(rs.getBigDecimal("price"))
            .remark(rs.getString("remark"))
            .build();
        list.add(coffee);
      }
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Coffee getCoffee(long id) {
    return null;
  }

  public boolean createCoffee(Map<String, Object> fieldMap) {
    return false;
  }

  public boolean updateCoffee(long id, Map<String, Object> fieldMap) {
    return false;
  }

  public boolean deleteCoffee(long id) {
    return false;
  }

}