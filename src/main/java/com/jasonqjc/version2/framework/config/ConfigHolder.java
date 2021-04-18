package com.jasonqjc.version2.framework.config;

import java.util.Properties;

import com.jasonqjc.version1.util.PropsUtil;

public final class ConfigHolder {
  private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
  
  public static String getJdbcDriver() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER_KEY);
  }
  
  public static String getJdbcUrl() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL_KEY);
  }
  
  public static String getJdbcUsername() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME_KEY);
  }
  
  public static String getJdbcPassword() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD_KEY);
  }
  
  public static String getScanBasePackage() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.BASE_PACKAGE_KEY);
  }
  
  public static String getJspPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JSP_PATH_KEY);
  }
  
  public static String getAssetPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.ASSET_PATH_KEY);
  }
  
}
