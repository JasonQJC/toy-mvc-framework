package com.jasonqjc.version2.framework;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Sets;
import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.annotation.Service;

public final class BeanClassHolder {
  private static final Set<Class<?>> ALL_CLASS_SET;
  private static final Set<Class<?>> SERVICE_CLASS_SET = Sets.newHashSet();
  private static final Set<Class<?>> CONTROLLER_CLASS_SET = Sets.newHashSet();
  private static final Set<Class<?>> BEAN_CLASS_SET = Sets.newHashSet();
  
  static {
    String basePackage = ConfigHolder.getScanBasePackage();
    ALL_CLASS_SET = ClassUtil.getClassSet(basePackage);
    if(CollectionUtils.isNotEmpty(ALL_CLASS_SET)) {
      ALL_CLASS_SET.forEach(item -> {
        if(item.isAnnotationPresent(Service.class)) {
          SERVICE_CLASS_SET.add(item);
        }
        if(item.isAnnotationPresent(Controller.class)) {
          SERVICE_CLASS_SET.add(item);
        }
      });
      BEAN_CLASS_SET.addAll(getServiceClassSet());
      BEAN_CLASS_SET.addAll(getControllerClassSet());
    }
  }

  public static Set<Class<?>> getAllClassSet() {
    return ALL_CLASS_SET;
  }

  public static Set<Class<?>> getServiceClassSet() {
    return SERVICE_CLASS_SET;
  }

  public static Set<Class<?>> getControllerClassSet() {
    return CONTROLLER_CLASS_SET;
  }

  public static Set<Class<?>> getBeanClassSet() {
    return BEAN_CLASS_SET;
  }

}
