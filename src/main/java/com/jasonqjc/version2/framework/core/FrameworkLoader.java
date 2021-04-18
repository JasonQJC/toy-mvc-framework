package com.jasonqjc.version2.framework.core;

import com.jasonqjc.version2.framework.util.ClassUtil;

public final class FrameworkLoader {
  public static void init() {
    Class<?>[] classArr = { BeanClassHolder.class, BeanClassInstanceHolder.class, IocContainer.class,
        ControllerHandlerHolder.class };
    for (Class<?> clas : classArr) {
      ClassUtil.loadClass(clas.getName(), true);
    }
  }
  
}
