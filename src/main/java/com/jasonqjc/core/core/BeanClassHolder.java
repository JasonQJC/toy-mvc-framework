package com.jasonqjc.core.core;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Sets;
import com.jasonqjc.core.annotation.Aspect;
import com.jasonqjc.core.annotation.Controller;
import com.jasonqjc.core.annotation.Service;
import com.jasonqjc.core.config.ConfigHolder;
import com.jasonqjc.core.proxy.AspectProxy;
import com.jasonqjc.core.util.ClassUtil;

public final class BeanClassHolder {
	private static final Set<Class<?>> ALL_CLASS_SET;
	private static final Set<Class<?>> SERVICE_CLASS_SET = Sets.newHashSet();
	private static final Set<Class<?>> CONTROLLER_CLASS_SET = Sets.newHashSet();
	private static final Set<Class<?>> ASPECT_CLASS_SET = Sets.newHashSet();
	private static final Set<Class<?>> BEAN_CLASS_SET = Sets.newHashSet();

	static {
		String basePackage = ConfigHolder.getScanBasePackage();
		ALL_CLASS_SET = ClassUtil.getClassSet(basePackage);
		if (CollectionUtils.isNotEmpty(ALL_CLASS_SET)) {
			ALL_CLASS_SET.forEach(item -> {
				if (item.isAnnotationPresent(Service.class)) {
					SERVICE_CLASS_SET.add(item);
				}
				if (item.isAnnotationPresent(Controller.class)) {
					CONTROLLER_CLASS_SET.add(item);
				}
				if (item.isAnnotationPresent(Aspect.class) && AspectProxy.class.isAssignableFrom(item) && !AspectProxy.class.equals(item)) {
					getAspectClassSet().add(item);
				}
			});
			BEAN_CLASS_SET.addAll(SERVICE_CLASS_SET);
			BEAN_CLASS_SET.addAll(CONTROLLER_CLASS_SET);
			BEAN_CLASS_SET.addAll(ASPECT_CLASS_SET);
		}
	}

	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : BEAN_CLASS_SET) {
			if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : BEAN_CLASS_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	public static Set<Class<?>> getAllClassSet() {
		return ALL_CLASS_SET;
	}

	public static Set<Class<?>> getServiceClassSet() {
		return SERVICE_CLASS_SET;
	}

	public static Set<Class<?>> getControllerClassSet() {
		return CONTROLLER_CLASS_SET;
	}

	public static Set<Class<?>> getBeanClassSet() {
		return BEAN_CLASS_SET;
	}

	public static Set<Class<?>> getAspectClassSet() {
		return ASPECT_CLASS_SET;
	}

}
