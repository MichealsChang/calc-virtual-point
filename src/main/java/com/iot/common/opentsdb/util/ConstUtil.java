package com.iot.common.opentsdb.util;

public class ConstUtil {
	public final static String CURRENT_PATH = getCurrentPath();
	public final static String FILE_PATH_SYS_CONFIG = "/"+CURRENT_PATH + "/conf/sysconfig.properties";

	
	private static String getCurrentPath() {
		/**
		 * windows
		 */
		//String strBasePath=System.getProperty("java.class.path").split(File.pathSeparator)[0];
		//strBasePath=strBasePath.substring(0,strBasePath.lastIndexOf('\\'));
		/**
		 * linus
		 */
		String strBasePath = System.getProperty("user.dir");
		return strBasePath;
	}

	public static void main(String[] args) {
		System.out.println(getCurrentPath()+"\\resources\\opentsdb.properties");
	}
}

 








