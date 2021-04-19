package com.jasonqjc.version2.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

@Data
public class ProxyChain {
  private final Class<?> targetClass;
  private final Object targetObject;
  private final Method targetMethod;
  private final MethodProxy methodProxy;
  private final Object[] methodParmas;
  
  private List<Proxy> proxyList = Lists.newArrayList();
  private int proxyIndex = 0;

  public Object doProxyChain() throws Throwable {
    Object methodResult = null;
    if(proxyIndex < proxyList.size()) {
      methodResult = proxyList.get(proxyIndex++).doProxy(this);
    } else {
      methodResult = methodProxy.invokeSuper(targetObject, methodParmas);
    }
    return methodResult;
  }
  
  public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy,
      Object[] methodParmas, List<Proxy> proxyList) {
    this.targetClass = targetClass;
    this.targetObject = targetObject;
    this.targetMethod = targetMethod;
    this.methodProxy = methodProxy;
    this.methodParmas = methodParmas;
    this.proxyList = proxyList;
  }
  
}
