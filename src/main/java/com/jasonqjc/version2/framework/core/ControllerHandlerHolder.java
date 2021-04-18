package com.jasonqjc.version2.framework.core;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.common.collect.Maps;
import com.jasonqjc.version2.framework.annotation.RequestHandler;
import com.jasonqjc.version2.framework.web.HandlerClassInfo;
import com.jasonqjc.version2.framework.web.Request;

public class ControllerHandlerHolder {
  private static final Map<Request,HandlerClassInfo> REQUEST_HANDLERCLASS_MAP = Maps.newHashMap();
  
  static {
    Set<Class<?>> controllerClassSet = BeanClassHolder.getControllerClassSet();
    if(CollectionUtils.isNotEmpty(controllerClassSet)) {
      for (Class<?> clas : controllerClassSet) {
        Method[] requestMethods = MethodUtils.getMethodsWithAnnotation(clas, RequestHandler.class);
        if(ArrayUtils.isNotEmpty(requestMethods)) {
          for (Method requestMethod : requestMethods) {
            RequestHandler annotation = requestMethod.getAnnotation(RequestHandler.class);
            String mapping = annotation.value();
            if(StringUtils.isNotBlank(mapping) && mapping.matches("^\\w+\\s+.+")) {
              String[] split = mapping.split(" ", 2);
              if(split != null && split.length == 2) {
                String method = split[0];
                String path = split[1].trim();
                Request request = new Request(method.toUpperCase(),path);
                HandlerClassInfo handlerClassInfo = new HandlerClassInfo(clas,requestMethod);
                REQUEST_HANDLERCLASS_MAP.put(request, handlerClassInfo);
              }
            }
          }
        }
      }
    }
  }
  
  public static HandlerClassInfo getHandlerClassInfo(String method,String requestPath) {
    return REQUEST_HANDLERCLASS_MAP.get(new Request(method.toUpperCase(),requestPath));
  }
  
}
