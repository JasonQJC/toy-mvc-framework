package com.jasonqjc.core.proxy;

public interface Proxy {
  Object doProxy(ProxyChain proxyChain) throws Throwable;
}
