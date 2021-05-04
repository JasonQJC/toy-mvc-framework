package com.jasonqjc.core.core;

import com.jasonqjc.core.proxy.AopHelper;
import com.jasonqjc.core.util.ClassUtil;

public final class FrameworkLoader {
  public static void init() {
    Class<?>[] classArr = { BeanClassHolder.class, 
                            BeanClassInstanceHolder.class, 
                            AopHelper.class,
                            IocInjector.class,
                            ControllerHandlerHolder.class };
    for (Class<?> clas : classArr) {
      ClassUtil.loadClass(clas.getName(), true);
    }
  }
  
}
