package com.jasonqjc.version2.framework.proxy;

import java.lang.reflect.Method;

import com.jasonqjc.version1.util.DBUtil;
import com.jasonqjc.version2.framework.annotation.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionProxy implements Proxy {

  private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<>() {
    @Override
    protected Boolean initialValue() {
      return false;
    }
  };

  @Override
  public Object doProxy(ProxyChain proxyChain) throws Throwable {
    Boolean flag = FLAG_HOLDER.get();
    Method targetMethod = proxyChain.getTargetMethod();
    Object result = null;
    if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
      FLAG_HOLDER.set(true);
      try {
        DBUtil.beginTransaction();
        log.debug("begin transaction");
        result = targetMethod.invoke(proxyChain.getTargetObject(), proxyChain.getMethodParmas());
        log.debug("commit transaction");
        DBUtil.commitTransaction();
      } catch (Exception e) {
        log.debug("rollback transaction");
        DBUtil.rollbackTransaction();
        throw e;
      } finally {
        FLAG_HOLDER.remove();
      }
    } else {
    	result = proxyChain.doProxyChain();
    }
    return result;
  }

}
