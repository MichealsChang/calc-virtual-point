package com.iot.calcvirtualpoint.common.constants;

import java.nio.charset.Charset;
import java.util.ResourceBundle;

import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.server.ServerConfig;

public class Configuration {

	private static Object lock = new Object();
	private static Configuration config = null;
	private static ResourceBundle rb = null;
	private static final String CONFIG_FILE = "conf/config";

	private Configuration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}

	/**
	 * 服务ID
	 */
	public static String SERVICE_ID = "";
	/**
	 * 服务版本号
	 */
	public static String SERVICE_VERSION = "";
	/**
	 * 服务类型
	 */
	public static String SERVICE_TYPE = "";
	/**
	 * 服务域名
	 */
	public static String SERVICE_DOMAIN = "";

	/**
	 * 项目运行端口
	 */
	public static Integer PROJECT_RUNTIME_PORT = Integer.MAX_VALUE;
	/**
	 * 项目运行IP
	 */
	public static String PROJECT_RUNTIME_IP = "";

	/**
	 * 默认登录在线时间
	 */
	public static Integer DEFALUT_SCOPETIME = 60 * 60 * 24;

	/**
	 * 根域
	 */
	public static String SSO_DOMAIN = "";
	/** 环境变量 */
	public static String LJTH_DATASYN_ENV = "test";
	/** 同步平台相同数据是否保存进数据库,yes/no */
	public static String LJTH_DATASYN_IS_SAVE_SAME = "yes";
	/** 同步平台错误数据是否保存进数据库,yes/no */
	public static String LJTH_DATASYN_IS_SAVE_ERROR = "yes";

	public static int LJTH_DATASYN_DATAPAGE_SIZE = 50;

	public static int LJTH_DATASYN_THREADPOLL_SIZE = 10;

	public static int THREADPOLL_COLSE_WAITTIME = 30;

	public static Charset CHARSET = Charset.forName("UTF-8");

	public static String STATIC_RESOURSE_DISK_PATH;
	public static String STATIC_RESOURSE_HTTP_URL = "/res";

	public static ServerConfig SERVER_CONFIG;

	/**
	 * 日志目录位置
	 */
	public static String SYSTEM_LOGGING_ROOT = "/mywork/Iponitcal1.0/infolog";

	/**
	 * 大数据同步数据记录详细日志的表名的前部分，后面跟一位数字
	 */
	public static String LJTH_DATASYN_LOG_DETAIL_TABLE_NAME = "n_task_ipl_detail";

	/**
	 * tsdb IP
	 */
	public static String TSDB_IP = "192.168.60.201";
	public static int TSDB_PORT = 4242;

	public static long TASK_EXE_FREQUENCY = 6 * 60 * 1000;

	/** 对接大数据平台kafka 服务器配置 */
	public static String KAFKA_BROKERS = "192.168.60.154";
	public static String KAFKA_TOPIC = "proddata-topic";

	public static long CALC_WAIT_TIME = 30 * 1000;

	private static void init() {

		Configuration conf = getInstance();

		try {

			SERVICE_ID = conf.getValue("service.id");
			SERVICE_VERSION = conf.getValue("service.version");
			SERVICE_TYPE = conf.getValue("service.type");
			SERVICE_DOMAIN = conf.getValue("service.domain");
			PROJECT_RUNTIME_PORT = NumberUtils.toInt(System.getProperty(ConstantParams.SYSTEM_PROJECT_PORT), Integer.MAX_VALUE);
			PROJECT_RUNTIME_IP = System.getProperty(ConstantParams.SYSTEM_PROJECT_IP);
			SSO_DOMAIN = conf.getValue("sso.domain");

			LJTH_DATASYN_DATAPAGE_SIZE = NumberUtils.toInt(conf.getValue("ljth.datasyn.datapage.size"), LJTH_DATASYN_DATAPAGE_SIZE);
			LJTH_DATASYN_THREADPOLL_SIZE = NumberUtils.toInt(conf.getValue("ljth.datasyn.threadpoll.size"), LJTH_DATASYN_THREADPOLL_SIZE);
			THREADPOLL_COLSE_WAITTIME = NumberUtils.toInt(conf.getValue("threadpoll.colse.waittime"), THREADPOLL_COLSE_WAITTIME);

			String str1 = conf.getValue("static_resourse_http_url");
			if (StringUtils.isNotBlank(str1)) {
				STATIC_RESOURSE_HTTP_URL = str1;
			}
			STATIC_RESOURSE_DISK_PATH = conf.getValue("static_resourse_disk_path");

			String str = conf.getValue("logging.root");
			if (StringUtils.isNotBlank(str)) {
				SYSTEM_LOGGING_ROOT = str;
			}

			DEFALUT_SCOPETIME = NumberUtils.toInt(conf.getValue("defalut.scopetime"), DEFALUT_SCOPETIME);

			TSDB_IP = conf.getValue("tsdb.ip");
			TSDB_PORT = NumberUtils.toInt(conf.getValue("tsdb.port"), 4242);
			TASK_EXE_FREQUENCY = NumberUtils.toLong(conf.getValue("task.exe.frequency"));

			KAFKA_BROKERS = conf.getValue("kafka.brokers");
			KAFKA_TOPIC = conf.getValue("kafka.topic");

			CALC_WAIT_TIME = NumberUtils.toLong(conf.getValue("calc.wait.time"), CALC_WAIT_TIME);

		} catch (Exception e) {
			LogUtils.error(e.getMessage(), new Throwable(e));
		}

	}

	static {
		init();
	}

	/**
	 * 初始化本地文件
	 */
	public static void initLocalConf() {
		ResourceBundle.clearCache();
		rb = ResourceBundle.getBundle(CONFIG_FILE);
		config = null;
		init();
	}

	public static Configuration getInstance() {
		synchronized (lock) {
			if (null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}

	public String getValue(String key) {
		return (rb.getString(key));
	}

	public static synchronized void setServerConfig(ServerConfig serverConfig) {
		if (SERVER_CONFIG != null) {
			return;
		}
		SERVER_CONFIG = serverConfig;
	}
}
