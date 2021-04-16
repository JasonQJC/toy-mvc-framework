package com.jasonqjc.version1.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DBUtil {
  private static final String DRIVER;
  private static final String URL;
  private static final String USERNAME;
  private static final String PASSWORD;
  private static final QueryRunner QUERY_RUNNER = new QueryRunner();
  private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

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

  public static <T> List<T> getEntityList(Class<T> entityClass, String sql, Object... params) {
    try (Connection conn = getConnection()) {
      return QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      CONNECTION_HOLDER.remove();
    }
    return null;
  }

  public static <T> T getEntity(Class<T> entityClass, String sql, Object... params) {
    try (Connection conn = getConnection()) {
      return QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      CONNECTION_HOLDER.remove();
    }
    return null;
  }

  public List<Map<String, Object>> executeQuery(String sql, Object... params) {
    List<Map<String, Object>> result = null;
    try {
      Connection conn = getConnection();
      result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static int executeUpdate(String sql, Object... params) {
    int rows = 0;
    try {
      Connection conn = getConnection();
      rows = QUERY_RUNNER.update(conn, sql, params);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rows;
  }

  public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
    if (CollectionUtils.isEmpty(fieldMap.keySet())) {
      log.error("can not insert entity: fieldMap is empty");
      return false;
    }

    String sql = "INSERT INTO " + getTableName(entityClass);
    StringBuilder columns = new StringBuilder("(");
    StringBuilder values = new StringBuilder("(");
    for (String fieldName : fieldMap.keySet()) {
      columns.append(fieldName).append(", ");
      values.append("?, ");
    }
    columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
    values.replace(values.lastIndexOf(", "), values.length(), ")");
    sql += columns + " VALUES " + values;

    Object[] params = fieldMap.values().toArray();

    return executeUpdate(sql, params) == 1;
  }

  public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
    if (CollectionUtils.isEmpty(fieldMap.keySet())) {
      log.error("can not update entity: fieldMap is empty");
      return false;
    }

    String sql = "UPDATE " + getTableName(entityClass) + " SET ";
    StringBuilder columns = new StringBuilder();
    for (String fieldName : fieldMap.keySet()) {
      columns.append(fieldName).append(" = ?, ");
    }
    sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";

    List<Object> paramList = Lists.newArrayList();
    paramList.addAll(fieldMap.values());
    paramList.add(id);
    Object[] params = paramList.toArray();

    return executeUpdate(sql, params) == 1;
  }

  public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
    String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
    return executeUpdate(sql, id) == 1;
  }

  public static void executeSqlFile(String filePath) {
    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      String sql;
      while ((sql = reader.readLine()) != null) {
        executeUpdate(sql);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getTableName(Class<?> entityClass) {
    return entityClass.getSimpleName().toLowerCase();
  }

  public static Connection getConnection() {
    Connection conn = CONNECTION_HOLDER.get();
    if (conn == null) {
      try {
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      } catch (SQLException e) {
        e.printStackTrace();
        log.error("get Connection fail!", e);
      }
    }
    return conn;
  }

}
