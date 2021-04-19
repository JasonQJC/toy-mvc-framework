package com.jasonqjc.version2.framework.proxy;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AspectProxy implements Proxy {

  @Override
  public Object doProxy(ProxyChain proxyChain) throws Throwable {
    Object result = null;
    
    Class<?> cls = proxyChain.getTargetClass();
    Method method = proxyChain.getTargetMethod();
    Object[] params = proxyChain.getMethodParmas();
    
    begin();
    
    try {
      if(intercept(cls, method, params)) {
        before(cls, method, params);
        result = proxyChain.doProxyChain();
        after(cls, method, params);
      } else {
        result = proxyChain.doProxyChain(); 
      }
    } catch (Throwable e) {
      log.error("proxy fail",e);
      error(cls,method,params,e);
      throw e;
    } finally {
      end();
    }
    
    return result;
  }

  public void end() {
  }

  public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
  }

  public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
  }

  public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
  }
  
  public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
    return true;
  }

  public void begin() {
  }

}
