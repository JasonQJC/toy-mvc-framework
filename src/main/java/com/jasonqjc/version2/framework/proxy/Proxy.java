package com.jasonqjc.version2.framework.proxy;

public interface Proxy {
  Object doProxy(ProxyChain proxyChain) throws Throwable;
}
