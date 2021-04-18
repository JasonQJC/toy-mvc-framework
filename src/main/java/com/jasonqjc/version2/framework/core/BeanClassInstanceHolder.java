package com.jasonqjc.version2.framework.core;

import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.core.util.ReflectionUtil;

import com.google.common.collect.Maps;

public final class BeanClassInstanceHolder {

  private static final Map<Class<?>,Object> BEAN_MAP = Maps.newHashMap();

  static {
   Set<Class<?>> classSet = BeanClassHolder.getAllClassSet();
   for (Class<?> clas : classSet) {
     Object object = ReflectionUtil.instantiate(clas);
     BEAN_MAP.put(clas, object);
   }
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> clas) {
    return (T) BEAN_MAP.get(clas);
  }
  
  public static Map<Class<?>,Object> getBeanMap() {
    return BEAN_MAP;
  }
  
}
