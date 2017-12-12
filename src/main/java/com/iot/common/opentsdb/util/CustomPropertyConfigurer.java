package com.iot.common.opentsdb.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomPropertyConfigurer {
	private static Logger log = Logger.getLogger(CustomPropertyConfigurer.class);
	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();
	private static String path = "/opentsdb.properties";
	
	public static void initProperties() {
		  Properties properties = null;
		  try{
			  FileInputStream is = new FileInputStream(path);
			  BufferedReader bf = new BufferedReader(new InputStreamReader(is,"utf-8"));  
			  properties = new Properties();
			  properties.load(bf);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  for (Object key : properties.keySet()) {
				String keyStr = key.toString();
				String value = properties.getProperty(keyStr).trim();
				ctxPropertiesMap.put(keyStr, value);
		  }
	}
	public static String getConfigProperty(String name) {
		return (String) ctxPropertiesMap.get(name);
	}
}
