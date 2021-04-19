package com.jasonqjc.test;

import java.lang.reflect.Method;

import com.jasonqjc.version2.framework.annotation.Aspect;
import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.proxy.AspectProxy;

import lombok.extern.slf4j.Slf4j;

@Aspect(Controller.class)
@Slf4j
public class ControllerAspect extends AspectProxy {

  private long begin;
  
  @Override
  public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    log.debug("--------------------bengin------------------");
    begin = System.currentTimeMillis();
    log.debug("class: {}", cls.getName());
    log.debug("method: {}", method.getName());
  }

  @Override
  public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
    log.debug("time: {}", System.currentTimeMillis() - begin);
    log.debug("--------------------end------------------");
  }
  
}
