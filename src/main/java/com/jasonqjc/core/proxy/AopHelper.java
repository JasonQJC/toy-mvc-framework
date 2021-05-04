package com.jasonqjc.core.proxy;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jasonqjc.core.annotation.Aspect;
import com.jasonqjc.core.core.BeanClassHolder;
import com.jasonqjc.core.core.BeanClassInstanceHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AopHelper {

  static {
    try {
      Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
      Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
      for (Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
        Class<?> targetClass = targetEntry.getKey();
        List<Proxy> proxyList = targetEntry.getValue();
        Object proxy = ProxyManager.createProxy(targetClass, proxyList);
        BeanClassInstanceHolder.setBean(targetClass, proxy);
      }
    } catch (Exception e) {
      log.error("aop failure", e);
    }
  }

  private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
    Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
    addAspectProxy(proxyMap);
    addTransactionProxy(proxyMap);
    return proxyMap;
  }
  
  private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
	//<切面类，被代理类>
	Set<Class<?>> proxyClassSet = BeanClassHolder.getAspectClassSet();
    for (Class<?> proxyClass : proxyClassSet) {
      if (proxyClass.isAnnotationPresent(Aspect.class)) {
        Aspect aspect = proxyClass.getAnnotation(Aspect.class);
        Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
        proxyMap.put(proxyClass, targetClassSet);
      }
    }
  }
  
  private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
    Set<Class<?>> targetClassSet = Sets.newHashSet();
    Class<? extends Annotation> annotation = aspect.value();
    if (annotation != null && !annotation.equals(Aspect.class)) {
      targetClassSet.addAll(BeanClassHolder.getClassSetByAnnotation(annotation));
    }
    return targetClassSet;
  }

  private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
    proxyMap.put(TransactionProxy.class, BeanClassHolder.getServiceClassSet());
  }

  private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
    Map<Class<?>, List<Proxy>> targetMap = Maps.newHashMap();
    for (Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
      Class<?> proxyClass = proxyEntry.getKey();
      Set<Class<?>> targetClassSet = proxyEntry.getValue();
      for (Class<?> targetClass : targetClassSet) {
        Proxy proxy = (Proxy) proxyClass.getConstructor().newInstance();
        if (targetMap.containsKey(targetClass)) {
          targetMap.get(targetClass).add(proxy);
        } else {
          List<Proxy> proxyList = Lists.newArrayList();
          proxyList.add(proxy);
          targetMap.put(targetClass, proxyList);
        }
      }
    }
    return targetMap;
  }
}
