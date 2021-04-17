package com.jasonqjc.version2.framework;

public final class FrameworkLoader {
  public static void init() {
    Class<?>[] classArr = { BeanClassHolder.class, BeanClassInstanceHolder.class, IocContainer.class,
        ControllerHandlerHolder.class };
    for (Class<?> clas : classArr) {
      ClassUtil.loadClass(clas.getName(), true);
    }
  }
  
  public static void main(String[] args) {
    init();
  }
}
