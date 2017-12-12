package com.iot.calcvirtualpoint.common.util;

import org.apache.log4j.Logger;

public class LogUtils {

	/** 访问日志 */
	protected static final Logger ACCESS_LOG = Logger.getLogger("access");
	
	/** 接口层 API 日志 **/
	protected static final Logger SERVICE_API_LOG = Logger.getLogger("api");

	/** 业务服务日志 */
	protected static final Logger SERVICE_LOG = Logger.getLogger("service");

	/** 调式日志 */
	protected static final Logger DEBUG_LOG = Logger.getLogger("debug_loglogger");
	
	/** 性能 */
	protected static final Logger PERF_LOG = Logger.getLogger("perf");

	/** 未被处理，被系统捕获的例外 */
	protected static final Logger EXCEPTION_LOG = Logger.getLogger("exception");

	/** 报警阀值日志 */
	protected static final Logger COM_ALARM_VALUE = Logger.getLogger("alarm");
	/** RPC日志 */
	protected static final Logger SERVICE_RPC_LOG = Logger.getLogger("rpc");
	/** 隆基泰和数据同步日志 */
    protected static final Logger SYN_DATA_LOG = Logger.getLogger("syndata");
    /** 隆基泰和数据同步错误日志 */
    protected static final Logger SYN_DATA_ERROR_LOG = Logger.getLogger("syndataerror");

    /** 暂时无用 */
	public static void serviceRpcLog(String log) {
		SERVICE_RPC_LOG.info(log);
	}
	/** 暂时无用 */
	public static void perfLog(String log) {
        PERF_LOG.info(log);
	}
	
	public static void perfDebugLog(String log) {
		if (PERF_LOG.isDebugEnabled()) {
			PERF_LOG.debug(log);
		}
	}

	public static void accessLog(String log) {
		ACCESS_LOG.info(log);
	}

	public static void serviceApiLog(String log) {
		SERVICE_API_LOG.info(log);
	}

	public static void serviceApiDebugLog(String log) {
		if (SERVICE_API_LOG.isDebugEnabled()) {
			SERVICE_API_LOG.debug(log);
		}
	}

	public static void serviceLog(String message) {
		SERVICE_LOG.info(message);
	}

	public static boolean serviceIsDebugEnabled() {
		return SERVICE_LOG.isDebugEnabled();
	}

	public static void serviceDebugLog(String message) {
		if (SERVICE_LOG.isDebugEnabled()) {
			SERVICE_LOG.debug(message);
		}
	}

	public static void error(String message, Throwable e) {
		EXCEPTION_LOG.error(message, e);
	}

	public static void error(String message) {
		EXCEPTION_LOG.error(message);
	}

	public static void comAlarmValue(String message) {
		COM_ALARM_VALUE.info(message);
	}
	
	public static void syndataLog(String message) {
        SYN_DATA_LOG.info(message);
    }
    
    public static void syndataerrorLog(String message) {
        SYN_DATA_ERROR_LOG.info(message);
    }
    
    public static void debugLog(String message) {
    	DEBUG_LOG.debug(message);
    }

}
