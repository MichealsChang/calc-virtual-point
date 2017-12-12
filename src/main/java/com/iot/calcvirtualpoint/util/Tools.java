package com.iot.calcvirtualpoint.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tools {

	public static Object getValueByGetMethodName(Object obj, int i) {
		Method method = null;
		Object value = null;
		String getter = "getP" + i;
		try {
			method = obj.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(obj, new Object[] {});
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static double randomDoubleNum(double start, double end) {
		return ((end - start) * Math.random() + start);
	}
	
}
