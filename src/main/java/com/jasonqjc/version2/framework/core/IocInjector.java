package com.jasonqjc.version2.framework.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.jasonqjc.version2.framework.annotation.Inject;

public final class IocInjector {
  static {
    Map<Class<?>, Object> beanMap = BeanClassInstanceHolder.getBeanMap();
    if(MapUtils.isNotEmpty(beanMap)) {
      for (Entry<Class<?>, Object> entry : beanMap.entrySet()) {
        Class<?> clas = entry.getKey();
        Object object = entry.getValue();
        Field[] needInjectFields = FieldUtils.getFieldsWithAnnotation(clas, Inject.class);
        if(ArrayUtils.isNotEmpty(needInjectFields)) {
          for (Field field : needInjectFields) {
            Class<?> fieldClass = field.getType();
            if(beanMap.containsKey(fieldClass)) {
              try {
                FieldUtils.writeField(field, object, beanMap.get(fieldClass), true);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
    }
  }
}
