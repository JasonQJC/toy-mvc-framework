package com.jasonqjc.test;


import java.lang.reflect.Method;

import com.jasonqjc.version2.framework.annotation.Aspect;
import com.jasonqjc.version2.framework.annotation.Controller;
import com.jasonqjc.version2.framework.proxy.AspectProxy;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

	
	private long begin;

	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		System.out.println("--------------------bengin------------------");
		begin = System.currentTimeMillis();
		System.out.println("class: " + cls.getName());
		System.out.println("method: " + method.getName());
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
		System.out.println("time: " + (System.currentTimeMillis() - begin));
		System.out.println("----------------------end-------------------");
	}

}
