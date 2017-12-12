package com.iot.calcvirtualpoint.common.runtime;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 直接获取bean
 * 
 * @author daizq
 * 
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext acx)
			throws BeansException {
		ApplicationContextHolder.applicationContext = acx;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) ApplicationContextHolder.applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) {
		return (T) ApplicationContextHolder.applicationContext.getBean(clazz);
	}

}
