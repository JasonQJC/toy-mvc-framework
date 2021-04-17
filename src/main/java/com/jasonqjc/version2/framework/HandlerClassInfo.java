package com.jasonqjc.version2.framework;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlerClassInfo {

  private Class<?> controllerClass;
  private Method actionMethod;
  
}
