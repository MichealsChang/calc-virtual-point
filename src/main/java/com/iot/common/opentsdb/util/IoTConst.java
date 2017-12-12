package com.iot.common.opentsdb.util;


public class IoTConst {

//	public static final String IOT_DATASOURCE_NAME = "jdbc/iotdb";
	public static final String IOT_DATASOURCE_NAME = "ds.bigdata";
	
	//-------------------------------------------------------------------------------------------------------
	
	//设备发送的消息
	private static final int MSG_DEVICE_BASE 	= 10;
	public static final int MSG_DEVICE_LOGIN 	= MSG_DEVICE_BASE + 1;			//设备登陆
	public static final int MSG_DEVICE_LOGOUT 	= MSG_DEVICE_BASE + 2;			//设备注销
	public static final int MSG_DEVICE_DATA 	= MSG_DEVICE_BASE + 3;			//上报数据
	public static final int MSG_DEVICE_WARN 	= MSG_DEVICE_BASE + 4;			//设备告警
	public static final int MSG_DEVICE_PARAM 	= MSG_DEVICE_BASE + 5;			//发送配置数据
	
	//中心平台发送的消息
	private static final int MSG_SERVER_BASE 	= 50;
	public static final int MSG_SERVER_PARAM 	= MSG_SERVER_BASE + 1;			//参数设置
	
	public static final String TOPIC_RTU_OUT	= "RTU/OUT/";					//主题：中心发送到设备的消息
	public static final String TOPIC_RTU_IN 	= "RTU/IN/";					//主题：设备发送到中心的消息
	
	public static final String READING_MSG_KEY_ADCD = "adcd";
	public static final String READING_MSG_KEY_OID = "oid";
	public static final String READING_MSG_KEY_DID = "dicd";
	public static final String READING_MSG_KEY_MID = "msid";
	public static final String READING_MSG_KEY_HYID = "hyid";
	public static final String READING_MSG_KEY_PID = "pid";
	public static final String READING_MSG_KEY_JFPG = "jfpg";
	public static final String READING_MSG_KEY_VAL = "val";				//测量值消息_测量值
	public static final String READING_MSG_KEY_TM = "tm";				//测量值消息_测量时间
	
	public static final String READING_MSG_KEY_STCD = "stcd";			//测量值消息_测站编码
	public static final String READING_MSG_KEY_SMID = "smid";			//测量值消息_测站通道ID
	public static final String READING_MSG_KEY_DMID = "dmid";			//测量值消息_设备通道ID
	public static final String READING_MSG_KEY_MTP = "mtp";				//测量值消息_通道类型（MEAS_TYPE_XXX）
	public static final String READING_MSG_KEY_DTP = "dtp";				//测量值消息_数据类型
	public static final String READING_MSG_KEY_LGTD = "lgtd";			//测量值消息_经度
	public static final String READING_MSG_KEY_LTTD = "lttd";			//测量值消息_纬度
	public static final String READING_MSG_KEY_GW_DTP = "gtp";		    //网关数据类型	
	public static final String READING_MSG_KEY_GWID = "gwid";		    //网关ID
	public static final String READING_MSG_KEY_GWNM = "gwnm";		    //网关NM
	public static final String READING_MSG_KEY_GWTM_LAST = "lasttm";    //网关LastTime
	public static final String READING_MSG_KEY_GW_LAT = "lat";          //网关经度
	public static final String READING_MSG_KEY_GW_LON = "lon";          //网关维度
	
//	public static final String EVENT_MSG_KEY_RID = "rid";				//规则ID
//	public static final String EVENT_MSG_KEY_EID = "eid";				//事件ID
//	public static final String EVENT_MSG_KEY_ENM = "enm";				//事件名称
//	public static final String EVENT_MSG_KEY_EDESC = "edesc";			//事件描述
//	public static final String EVENT_MSG_KEY_ESDT = "esdt";				//事件起始时间
//	public static final String EVENT_MSG_KEY_EEDT = "eedt";				//事件结束时间
//	public static final String EVENT_MSG_KEY_ENT = "ent";				//事件备注
//	public static final String EVENT_MSG_KEY_ETP = "etp";				//事件类型
//	public static final String EVENT_MSG_KEY_ESTAT = "estat";			//事件状态
//	public static final String EVENT_MSG_KEY_STNM = "stnm";				//测站名称
//	public static final String EVENT_MSG_KEY_STADDR = "staddr";			//测站地址
//	public static final String EVENT_MSG_KEY_SMID = "smid";				//测站通道ID
//	public static final String EVENT_MSG_KEY_SMNM = "smnm";				//测站通道名称
//	public static final String EVENT_MSG_KEY_DMID = "dmid";				//设备通道ID
//	public static final String EVENT_MSG_KEY_DMUNIT = "dmunit";			//设备通道单位
	
	//业务库同步
	public static final String SYNC_DB_TBNM = "sync_db_tbnm";			//同步业务库表名
	public static final String SYNC_DB_STTB_RIVER = "st_river_r";		//同步业务库表名
	public static final String SYNC_DB_STTB_PPTN = "st_pptn_r";			//同步业务库表名
	public static final String SYNC_DB_STTB_RSVR = "st_rsvr_r";			//同步业务库表名
	public static final String SYNC_DB_STTB_STBPRP = "st_stbprp_b";		//同步业务库表名
	
	public static final String SYNC_DB_GW = "IOT_GW";	    			//同步业务库表名
	public static final String SYNC_DB_GW_ASSET = "IOT_GW_ASSET";	    //同步业务库表名
	
//	public static String getSyncTableNameOfMeasType(String measType) {
//		switch (measType) {
//			case MEAS_TYPE_PPTN_DRP:
//				return SYNC_DB_STTB_PPTN;
//			case MEAS_TYPE_RIVER_Z:
//			case MEAS_TYPE_RIVER_Q:
//				return SYNC_DB_STTB_RIVER;
//			case MEAS_TYPE_RSVR_RZ:
//			case MEAS_TYPE_RSVR_W:
//			case MEAS_TYPE_RSVR_INQ:
//				return SYNC_DB_STTB_RSVR;
//		}
//		return "";
//	}
	
	//-------------------------------------------------------------------------------------------------------
	public static final String DATA_GW_TYPE_INFO 		= "gw";					//数据计算类型：原始值
	public static final String DATA_GW_TYPE_DSN 		= "dsn";				//数据计算类型：计算值
	public static final String DATA_GW_TYPE_HB 		    = "hb";					//数据计算类型：计算值

	public static final String DATA_CAL_TYPE_RAW 		= "raw";				//数据计算类型：原始值
	public static final String DATA_CAL_TYPE_CAL 		= "cal";				//数据计算类型：计算值
	
	public static final String DATA_CAL_FUNC_SUM		= "zimsum";				//公式：累计
	public static final String DATA_CAL_FUNC_MAX		= "max";				//公式：最大
	public static final String DATA_CAL_FUNC_MIN		= "min";				//公式：最小
	public static final String DATA_CAL_FUNC_AVG		= "avg";				//公式：平均
	public static final String DATA_CAL_FUNC_COUNT		= "count";				//公式：个数
	public static final String DATA_CAL_FUNC_FIRST		= "first";				//公式：第一个
	public static final String DATA_CAL_FUNC_LAST		= "last";				//公式：最后一个
	
	public static final String[] DATA_CAL_FUNCS = {
		DATA_CAL_FUNC_SUM,
		DATA_CAL_FUNC_MAX,
		DATA_CAL_FUNC_MIN,
		DATA_CAL_FUNC_AVG,
		DATA_CAL_FUNC_COUNT,
		DATA_CAL_FUNC_FIRST,
		DATA_CAL_FUNC_LAST
	};
	
	public static final String DATA_SRC_TYPE_ASSET 		= "asset";				//数据源类型：设备
	public static final String DATA_SRC_TYPE_DB 		= "db";					//数据源类型：数据库
	public static final String DATA_SRC_TYPE_FILE 		= "file";				//数据源类型：文件
	
	public static final String DATA_TAG_TYPE_RAW 		= "raw";				//数据标签类型：原始数据
	public static final String DATA_TAG_TYPE_CRT 		= "crt";				//数据标签类型：纠正数据
	public static final String DATA_TAG_TYPE_EXCP 		= "exce";				//数据标签类型：无效数据
	
	public static final String DATA_STATUS_ENABLE 		= "enable";				//数据状态：启用
	public static final String DATA_STATUS_DISABLE 		= "disable";			//数据状态：禁用
	public static final String DATA_STATUS_REMOVE 		= "remove";				//数据状态：删除

	public static final String WARN_SRC_TYPE_RULE 		= "rule";				//预警源类型：规则触发
	public static final String WARN_SRC_TYPE_DEVICE 	= "device";				//预警源类型：设备上报
	public static final String WARN_SRC_TYPE_MANUAL 	= "manual";				//预警源类型：手工填报
	
	public static final String WARN_STATUS_INIT 		= "init";				//预警状态：产生告警
	public static final String WARN_STATUS_SP_RECV 		= "sp_recv";			//预警状态：已发送监管人员
	public static final String WARN_STATUS_OP_RECV 		= "op_recv";			//预警状态：已发送维护人员
	public static final String WARN_STATUS_OP_CONF 		= "op_conf";			//预警状态：维护人员确认
	public static final String WARN_STATUS_OP_DONE 		= "op_done";			//预警状态：维护人员处理完毕
	public static final String WARN_STATUS_CLOSE 		= "close";				//预警状态：关闭告警
	
	public static final String WARN_LOG_INIT            = "init";               //预警日志类型：产生
    public static final String WARN_LOG_NOTICE          = "notice";             //预警日志类型：通知
    public static final String WARN_LOG_CLOSE           = "close";              //预警日志类型：关闭 

	public static final String RULE_GRP_ALM 			= "alm";				//规则分组：故障报警
	public static final String RULE_GRP_WRN 			= "wrn";				//规则分组：预警
	public static final String RULE_GRP_CRT 			= "crt";				//规则分组：提醒
	
	public static String getRuleGrpName(String grp) {
		switch (grp) {
			case RULE_GRP_ALM:
				return "故障报警";
			case RULE_GRP_WRN:
				return "预警";
			case RULE_GRP_CRT:
				return "提醒";
		}
		return "";
	}

	public static final String RULE_TRG_TYPE_ALL 		= "all";				//规则触发类型：全部条件满足时触发
	public static final String RULE_TRG_TYPE_ANY 		= "any";				//规则触发类型：任一条件满足时触发

	public static final String RULE_SCP_SINGLE 			= "single";				//规则分组：单站
	public static final String RULE_SCP_MULTI 			= "multi";				//规则分组：多站
	public static final String RULE_SCP_ADCD 			= "adcd";				//规则分组：行政区划
	public static final String RULE_SCP_BASIN 			= "basin";				//规则分组：流域
	
	public static final String RULE_TYPE_THRESHOLD		= "threshold";			//预警规则类型：阈值
	public static final String RULE_TYPE_DATARATE		= "rate";				//预警规则类型：数据变化率
	public static final String RULE_TYPE_DATAFLAT		= "flat";				//预警规则类型：数据平坦
	public static final String RULE_TYPE_DATADELAY		= "delay";				//预警规则类型：数据延迟
	public static final String RULE_TYPE_NODATA			= "none";				//预警规则类型：无数据
	public static final String RULE_TYPE_ANA		    = "ana";				//预警规则类型：相关性分析
	
	public static String getRuleTypeName(String type) {
		switch (type) {
			case RULE_TYPE_THRESHOLD:
				return "阈值";
			case RULE_TYPE_DATARATE:
				return "数据变化率";
			case RULE_TYPE_DATAFLAT:
				return "数据平坦";
			case RULE_TYPE_DATADELAY:
				return "数据延迟";
			case RULE_TYPE_NODATA:
				return "无数据";
		}
		return "";
	}
	
//	public static final String RULE_TYPE_INTERNAL_FUN	= "internal_fun";		//预警规则类型：数据计算（内部使用）
//	public static final String RULE_TYPE_THRESHOLD_L	= "threshold_l";		//预警规则类型：阈值（左）
//	public static final String RULE_TYPE_THRESHOLD_M	= "threshold_m";		//预警规则类型：阈值（中）
//	public static final String RULE_TYPE_THRESHOLD_R	= "threshold_r";		//预警规则类型：阈值（右）
//	public static final String RULE_TYPE_THRESHOLD_L2	= "threshold_l2";		//异常规则类型：阈值（左）
//	public static final String RULE_TYPE_THRESHOLD_M2	= "threshold_m2";		//异常规则类型：阈值（中）
//	public static final String RULE_TYPE_THRESHOLD_R2	= "threshold_r2";		//异常规则类型：阈值（右）
//	public static final String RULE_TYPE_THRESHOLD_AVG	= "threshold_avg";		//异常规则类型：阈值
	
	public static final String RESP_TYPE_SYS			= "sys";				//响应类型：系统
	public static final String RESP_TYPE_WECHAT			= "wechat";				//响应类型：微信
	public static final String RESP_TYPE_EAMIL			= "email";				//规则类型：邮件
	public static final String RESP_TYPE_SMS			= "sms";				//规则类型：短信
	public static final String RESP_TYPE_PHONE			= "phone";				//规则类型：电话
	
	public static final String TIME_UNIT_TYPE_YEAR		= "year";				//时间单位：年
	public static final String TIME_UNIT_TYPE_MONTH		= "month";				//时间单位：月
	public static final String TIME_UNIT_TYPE_DAY		= "day";				//时间单位：日
	public static final String TIME_UNIT_TYPE_HOUR		= "hour";				//时间单位：时
	public static final String TIME_UNIT_TYPE_MIN		= "min";				//时间单位：分
	public static final String TIME_UNIT_TYPE_SEC		= "sec";				//时间单位：秒
	public static final String TIME_UNIT_TYPE_MSEC		= "millisecond";		//时间单位：毫秒
	
	public static final String[] TIME_UNIT_TYPES = {
		TIME_UNIT_TYPE_YEAR, 
		TIME_UNIT_TYPE_MONTH,
		TIME_UNIT_TYPE_DAY,
		TIME_UNIT_TYPE_HOUR,
		TIME_UNIT_TYPE_MIN,
		TIME_UNIT_TYPE_SEC,
		TIME_UNIT_TYPE_MSEC
	};
	
	public static String getTimeUnitName(String timeUnitType) {
		switch (timeUnitType) {
			case TIME_UNIT_TYPE_YEAR:
				return "年";
			case TIME_UNIT_TYPE_MONTH:
				return "月";
			case TIME_UNIT_TYPE_DAY:
				return "日";
			case TIME_UNIT_TYPE_HOUR:
				return "小时";
			case TIME_UNIT_TYPE_MIN:
				return "分钟";
			case TIME_UNIT_TYPE_SEC:
				return "秒";
			case TIME_UNIT_TYPE_MSEC:
				return "毫秒";
		}
		return "";
	}
	
	public static final String OPERATOR_TYPE_L			= "l";					//操作符：<
	public static final String OPERATOR_TYPE_LE			= "le";					//操作符：<=
	public static final String OPERATOR_TYPE_G			= "g";					//操作符：>
	public static final String OPERATOR_TYPE_GE			= "ge";					//操作符：>=
	public static final String OPERATOR_TYPE_LG			= "lg";					//操作符：<>
	
	public static final String getOperatorName(String operator) {
		switch (operator) {
			case OPERATOR_TYPE_L:
				return "小于";
			case OPERATOR_TYPE_LE:
				return "小于等于";
			case OPERATOR_TYPE_G:
				return "大于";
			case OPERATOR_TYPE_GE:
				return "大于等于";
			case OPERATOR_TYPE_LG:
				return "不等于";
		}
		return "";
	}
	
	public static final String getOperatorVal(String operator) {
		switch (operator) {
			case OPERATOR_TYPE_L:
				return "<";
			case OPERATOR_TYPE_LE:
				return "<=";
			case OPERATOR_TYPE_G:
				return ">";
			case OPERATOR_TYPE_GE:
				return ">=";
			case OPERATOR_TYPE_LG:
				return "<>";
		}
		return "";
	}
	
	public static final String SITE_LOC_TYPE_STATIC		= "static";				//测站地址类型：固定站
	public static final String SITE_LOC_TYPE_MOVING		= "protable";			//测站位置类型：移动站
	
	public static final String MEAS_TYPE_COMMON_Z 		= "z";					//通道类型：通用_水位
	public static final String MEAS_TYPE_COMMON_Q 		= "q";					//通道类型：通用_流量
	public static final String MEAS_TYPE_COMMON_D 		= "d";					//通道类型：通用_管直径
	public static final String MEAS_TYPE_COMMON_S 		= "s";					//通道类型：通用_流速
	
	public static final String MEAS_TYPE_PPTN_DRP 		= "drp";				//通道类型：雨量_时段降水量
	public static final String MEAS_TYPE_RSVR_RZ 		= "rz";					//通道类型：水库_库上水位
	public static final String MEAS_TYPE_RSVR_W 		= "w";					//通道类型：水库_蓄水量
	public static final String MEAS_TYPE_RSVR_INQ 		= "inq";				//通道类型：水库_入库流量
	
	public static final String MEAS_TYPE_PIPE_H 		= "h";					//通道类型：管网_距井盖距离
	
	public static final String MEAS_TYPE_D_XDL 			= "d_xdl";				//通道类型：电池_蓄电量
	public static final String MEAS_TYPE_D_GZBZ 		= "d_gzbz";				//通道类型：电池_故障标志
	public static final String MEAS_TYPE_D_GZXQ 		= "d_gzxq";				//通道类型：电池_故障详情
	
	public static final String MEAS_TYPE_W_DDL 			= "w_ddl";				//通道类型：水质_电导率
	public static final String MEAS_TYPE_W_WD 			= "w_wd";				//通道类型：水质_温度
	public static final String MEAS_TYPE_W_YL 			= "w_yl";				//通道类型：水质_压力
	public static final String MEAS_TYPE_W_YD 			= "w_yd";				//通道类型：水质_盐度
	public static final String MEAS_TYPE_W_RAW1 		= "w_raw1";				//通道类型：水质_RAW1
	public static final String MEAS_TYPE_W_OXSAT1 		= "w_oxsat1";			//通道类型：水质_OXSAT1
	public static final String MEAS_TYPE_W_DO 			= "w_do";				//通道类型：水质_溶解氧
	public static final String MEAS_TYPE_W_OXSAT2 		= "w_oxsat2";			//通道类型：水质_溶解氧饱和度
	public static final String MEAS_TYPE_W_YSZD 		= "w_yszd";				//通道类型：水质_原始浊度
	public static final String MEAS_TYPE_W_ZD 			= "w_zd";				//通道类型：水质_浊度
	public static final String MEAS_TYPE_W_YSYLS 		= "w_ysyls";			//通道类型：水质_原始叶绿素
	public static final String MEAS_TYPE_W_YLS 			= "w_yls";				//通道类型：水质_叶绿素
	public static final String MEAS_TYPE_W_RAW2 		= "w_raw2";				//通道类型：水质_RAW2
	public static final String MEAS_TYPE_W_CDOM 		= "w_cdom";				//通道类型：水质_CDOM
	public static final String MEAS_TYPE_K_XSYND 		= "k_xsynd";			//通道类型：水质_硝酸盐浓度
	public static final String MEAS_TYPE_K_XSYD 		= "k_xsyd";				//通道类型：水质_硝酸盐氮
	public static final String MEAS_TYPE_K_LSYHL 		= "k_lsyhl";			//通道类型：水质_磷酸盐含量
	
	public static final String MEAS_TYPE_K_WD 			= "k_wd";				//通道类型：大气_温度
	public static final String MEAS_TYPE_K_SD 			= "k_sd";				//通道类型：大气_湿度
	public static final String MEAS_TYPE_K_QY 			= "k_qy";				//通道类型：大气_气压
	public static final String MEAS_TYPE_K_FS 			= "k_fs";				//通道类型：大气_风速
	public static final String MEAS_TYPE_K_ZDFS 		= "k_zdfs";				//通道类型：大气_最大风速
	public static final String MEAS_TYPE_K_FX 			= "k_fx";				//通道类型：大气_风向
	public static final String MEAS_TYPE_K_SQWD 		= "k_sqwd";				//通道类型：大气_湿球温度
	public static final String MEAS_TYPE_K_MGPJZ 		= "k_mgpjz";			//通道类型：大气_明光谱平均值
	public static final String MEAS_TYPE_K_AGPJZ 		= "k_agpjz";			//通道类型：大气_暗光谱平均值
	public static final String MEAS_TYPE_K_SCYDY 		= "k_scydy";			//通道类型：大气_数采仪电压
	
	public static final String MEAS_TYPE_HLJ_FX 		= "hlj_fx";				//通道类型：海流计_流向
	public static final String MEAS_TYPE_HLJ_LS 		= "hlj_ls";				//通道类型：海流计_流速
	
	public static final String MEAS_TYPE_NBPH 			= "nbph";				//通道类型：内部PH
	public static final String MEAS_TYPE_WBPH 			= "wbph";				//通道类型：外部PH
	
	
	public static String getMeasTypeName(String measType) {
		switch (measType) {
		case MEAS_TYPE_PPTN_DRP:
			return "雨量";
		case MEAS_TYPE_COMMON_Z:
			return "水位";
		case MEAS_TYPE_COMMON_Q:
			return "流量";
		case MEAS_TYPE_PIPE_H:
			return "距井盖距离";
		case MEAS_TYPE_RSVR_RZ:
			return "库上水位";
		case MEAS_TYPE_RSVR_W:
			return "蓄水量";
		case MEAS_TYPE_RSVR_INQ:
			return "入库流量";
		case MEAS_TYPE_D_XDL:
			return "电池蓄电量";
		case MEAS_TYPE_D_GZBZ:
			return "电池故障标志";
		case MEAS_TYPE_D_GZXQ:
			return "电池故障详情";
		case MEAS_TYPE_W_DDL:
			return "水质电导率";
		case MEAS_TYPE_W_WD:
			return "水质温度";
		case MEAS_TYPE_W_YL:
			return "水质压力";
		case MEAS_TYPE_W_YD:
			return "水质盐度";
		case MEAS_TYPE_W_RAW1:
			return "水质RAW1";
		case MEAS_TYPE_W_OXSAT1:
			return "水质OXSAT1";
		case MEAS_TYPE_W_DO:
			return "水质溶解氧";
		case MEAS_TYPE_W_OXSAT2:
			return "水质溶解氧饱和度";
		case MEAS_TYPE_W_YSZD:
			return "水质原始浊度";
		case MEAS_TYPE_W_ZD:
			return "水质浊度";
		case MEAS_TYPE_W_YSYLS:
			return "水质原始叶绿素";
		case MEAS_TYPE_W_YLS:
			return "水质叶绿素";
		case MEAS_TYPE_W_RAW2:
			return "水质RAW2";
		case MEAS_TYPE_W_CDOM:
			return "水质CDOM";
		case MEAS_TYPE_K_WD:
			return "大气温度";
		case MEAS_TYPE_K_SD:
			return "大气湿度";
		case MEAS_TYPE_K_QY:
			return "大气气压";
		case MEAS_TYPE_K_FS:
			return "大气风速";
		case MEAS_TYPE_K_ZDFS:
			return "大气最大风速";
		case MEAS_TYPE_K_FX:
			return "大气风向";
		case MEAS_TYPE_K_SQWD:
			return "大气湿球温度";
		case MEAS_TYPE_K_XSYND:
			return "水质硝酸盐浓度";
		case MEAS_TYPE_K_XSYD:
			return "水质硝酸盐氮";
		case MEAS_TYPE_K_MGPJZ:
			return "大气明光谱平均值";
		case MEAS_TYPE_K_AGPJZ:
			return "大气暗光谱平均值";
		case MEAS_TYPE_K_LSYHL:
			return "水质磷酸盐含量";
		case MEAS_TYPE_K_SCYDY:
			return "大气数采仪电压";
		case MEAS_TYPE_HLJ_FX:
			return "海流计流向";
		case MEAS_TYPE_HLJ_LS:
			return "海流计流速";
		case MEAS_TYPE_NBPH:
			return "内部PH";
		case MEAS_TYPE_WBPH:
			return "外部PH";
		}
		return "未知通道类型";
	}
	
	public static final String SITE_TYPE_PP 			= "PP";
	public static final String SITE_TYPE_BB 			= "BB";
	public static final String SITE_TYPE_TT 			= "TT";
	public static final String SITE_TYPE_DP 			= "DP";
	public static final String SITE_TYPE_ZQ 			= "ZQ";
	public static final String SITE_TYPE_ZZ 			= "ZZ";
	public static final String SITE_TYPE_WQ 			= "WQ";
	public static final String SITE_TYPE_RR 			= "RR";
	public static final String SITE_TYPE_MM 			= "MM";
	public static final String SITE_TYPE_SS 			= "SS";
	public static final String SITE_TYPE_DD 			= "DD";
	public static final String SITE_TYPE_ZG 			= "ZG";
	public static final String SITE_TYPE_ZB 			= "ZB";
	public static final String SITE_TYPE_RQ 			= "RQ";
	public static final String SITE_TYPE_II 			= "II";
	public static final String SITE_TYPE_VV 			= "VV";
	public static final String SITE_TYPE_PZ 			= "PZ";
	public static final String SITE_TYPE_PQ 			= "PQ";
	public static final String SITE_TYPE_HY 			= "HY";
	
	public static String getSiteTypeName(String siteType) {
		switch (siteType) {
			case SITE_TYPE_PP:
				return "雨量站";
			case SITE_TYPE_BB:
				return "蒸发站";
			case SITE_TYPE_TT:
				return "潮位站";
			case SITE_TYPE_DP:
				return "泵站";
			case SITE_TYPE_ZQ:
				return "河道水文站";
			case SITE_TYPE_ZZ:
				return "河道水位站";
			case SITE_TYPE_WQ:
				return "水质站";
			case SITE_TYPE_RR:
				return "水库水文站";
			case SITE_TYPE_MM:
				return "气象站";
			case SITE_TYPE_SS:
				return "墒情站";
			case SITE_TYPE_DD:
				return "堰闸水文站";
			case SITE_TYPE_ZG:
				return "地下水站";
			case SITE_TYPE_ZB:
				return "分洪水位站";
			case SITE_TYPE_RQ:
				return "水库水位站";
			case SITE_TYPE_II:
				return "图像站";
			case SITE_TYPE_VV:
				return "视频站";
			case SITE_TYPE_PZ:
				return "管网水位站";
			case SITE_TYPE_PQ:
				return "管网流量站";
			case SITE_TYPE_HY:
				return "海洋监测站";
		}
		return "未知测站类型";
	}
	
	//设备包含的通道类型（设备输出）
	public static String[] getAssetMeasTypeBySttp(String siteType) {
		switch (siteType) {
			case SITE_TYPE_PP:
				return new String[]{MEAS_TYPE_PPTN_DRP};
			case SITE_TYPE_ZQ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q,MEAS_TYPE_PPTN_DRP};
			case SITE_TYPE_ZZ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q};
			case SITE_TYPE_PZ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q,MEAS_TYPE_PIPE_H};
			case SITE_TYPE_RR:
				return new String[]{MEAS_TYPE_RSVR_RZ,MEAS_TYPE_RSVR_INQ,MEAS_TYPE_RSVR_W};
			case SITE_TYPE_RQ:
				return new String[]{MEAS_TYPE_RSVR_RZ};
			case SITE_TYPE_HY:
				return new String[]{
						MEAS_TYPE_D_XDL 	,
						MEAS_TYPE_D_GZBZ 	,
						MEAS_TYPE_D_GZXQ 	,
						MEAS_TYPE_W_DDL 	,
						MEAS_TYPE_W_WD 		,
						MEAS_TYPE_W_YL 		,
						MEAS_TYPE_W_YD 		,
						MEAS_TYPE_W_RAW1 	,
						MEAS_TYPE_W_OXSAT1 	,
						MEAS_TYPE_W_DO 		,
						MEAS_TYPE_W_OXSAT2 	,
						MEAS_TYPE_W_YSZD 	,
						MEAS_TYPE_W_ZD 		,
						MEAS_TYPE_W_YSYLS 	,
						MEAS_TYPE_W_YLS 	,
						MEAS_TYPE_W_RAW2 	,
						MEAS_TYPE_W_CDOM 	,
						MEAS_TYPE_K_XSYND 	,
						MEAS_TYPE_K_XSYD 	,
						MEAS_TYPE_K_LSYHL 	,
						MEAS_TYPE_K_WD 		,
						MEAS_TYPE_K_SD 		,
						MEAS_TYPE_K_QY 		,
						MEAS_TYPE_K_FS 		,
						MEAS_TYPE_K_ZDFS 	,
						MEAS_TYPE_K_FX 		,
						MEAS_TYPE_K_SQWD 	,
						MEAS_TYPE_K_MGPJZ 	,
						MEAS_TYPE_K_AGPJZ 	,
						MEAS_TYPE_K_SCYDY 	,
						MEAS_TYPE_NBPH 		,
						MEAS_TYPE_WBPH 		,
						"hlj_1_ls",
						"hlj_2_ls",
						"hlj_3_ls",
						"hlj_4_ls",
						"hlj_5_ls",
						"hlj_6_ls",
						"hlj_7_ls",
						"hlj_8_ls",
						"hlj_9_ls",
						"hlj_10_ls",
						"hlj_11_ls",
						"hlj_12_ls",
						"hlj_13_ls",
						"hlj_14_ls",
						"hlj_15_ls",
						"hlj_16_ls",
						"hlj_17_ls",
						"hlj_18_ls",
						"hlj_19_ls",
						"hlj_20_ls",
						"hlj_1_fx",
						"hlj_2_fx",
						"hlj_3_fx",
						"hlj_4_fx",
						"hlj_5_fx",
						"hlj_6_fx",
						"hlj_7_fx",
						"hlj_8_fx",
						"hlj_9_fx",
						"hlj_10_fx",
						"hlj_11_fx",
						"hlj_12_fx",
						"hlj_13_fx",
						"hlj_14_fx",
						"hlj_15_fx",
						"hlj_16_fx",
						"hlj_17_fx",
						"hlj_18_fx",
						"hlj_19_fx",
						"hlj_20_fx"
						};
		}
		
		return new String[]{};
	}
	
	//测站包含的通道类型（系统输入）
	public static String[] getSiteMeasTypeBySttp(String siteType) {
		switch (siteType) {
			case SITE_TYPE_PP:
				return new String[]{MEAS_TYPE_PPTN_DRP};
			case SITE_TYPE_ZQ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q,MEAS_TYPE_PPTN_DRP};
			case SITE_TYPE_ZZ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q};
			case SITE_TYPE_PZ:
				return new String[]{MEAS_TYPE_COMMON_Z,MEAS_TYPE_COMMON_Q,MEAS_TYPE_PIPE_H};
			case SITE_TYPE_RR:
				return new String[]{MEAS_TYPE_RSVR_RZ,MEAS_TYPE_RSVR_INQ,MEAS_TYPE_RSVR_W};
			case SITE_TYPE_RQ:
				return new String[]{MEAS_TYPE_RSVR_RZ};
			case SITE_TYPE_HY:
//				return new String[]{MEAS_TYPE_W_WD, MEAS_TYPE_W_YD, MEAS_TYPE_W_YLS, MEAS_TYPE_W_DO, MEAS_TYPE_W_ZD};
				return new String[]{
						MEAS_TYPE_W_DDL 	,
						MEAS_TYPE_W_WD 		,
						MEAS_TYPE_W_YL 		,
						MEAS_TYPE_W_YD 		,
						MEAS_TYPE_W_RAW1 	,
						MEAS_TYPE_W_OXSAT1 	,
						MEAS_TYPE_W_DO 		,
						MEAS_TYPE_W_OXSAT2 	,
						MEAS_TYPE_W_YSZD 	,
						MEAS_TYPE_W_ZD 		,
						MEAS_TYPE_W_YSYLS 	,
						MEAS_TYPE_W_YLS 	,
						MEAS_TYPE_W_RAW2 	,
						MEAS_TYPE_W_CDOM 	,
						MEAS_TYPE_K_WD 		,
						MEAS_TYPE_K_SD 		,
						MEAS_TYPE_K_QY 		,
						MEAS_TYPE_K_FS 		,
						MEAS_TYPE_K_ZDFS 	,
						MEAS_TYPE_K_FX 		,
						MEAS_TYPE_K_SQWD 	,
						MEAS_TYPE_K_XSYND 	,
						MEAS_TYPE_K_XSYD 	,
						MEAS_TYPE_K_MGPJZ 	,
						MEAS_TYPE_K_AGPJZ 	,
						MEAS_TYPE_K_LSYHL 	,
						MEAS_TYPE_K_SCYDY 	,
						MEAS_TYPE_NBPH 		,
						MEAS_TYPE_WBPH 		,
						"hlj_1_ls",
						"hlj_2_ls",
						"hlj_3_ls",
						"hlj_4_ls",
						"hlj_5_ls",
						"hlj_6_ls",
						"hlj_7_ls",
						"hlj_8_ls",
						"hlj_9_ls",
						"hlj_10_ls",
						"hlj_11_ls",
						"hlj_12_ls",
						"hlj_13_ls",
						"hlj_14_ls",
						"hlj_15_ls",
						"hlj_16_ls",
						"hlj_17_ls",
						"hlj_18_ls",
						"hlj_19_ls",
						"hlj_20_ls",
						"hlj_1_fx",
						"hlj_2_fx",
						"hlj_3_fx",
						"hlj_4_fx",
						"hlj_5_fx",
						"hlj_6_fx",
						"hlj_7_fx",
						"hlj_8_fx",
						"hlj_9_fx",
						"hlj_10_fx",
						"hlj_11_fx",
						"hlj_12_fx",
						"hlj_13_fx",
						"hlj_14_fx",
						"hlj_15_fx",
						"hlj_16_fx",
						"hlj_17_fx",
						"hlj_18_fx",
						"hlj_19_fx",
						"hlj_20_fx"
						};
		}
		
		return new String[]{};
	}
	
	public static final String ASSET_MODEL_UNKNOWN			= "unknown";			//设备型号：未知
	public static final String ASSET_MODEL_RICHWAY_1		= "richway_1";			//设备型号：水资源RTU
	public static final String ASSET_MODEL_RICHWAY_2		= "richway_2";			//设备型号：MQTT RTU
	
	public static final String ASSET_MODEL_PROTOCOL_MQTT	= "MQTT";				//设备协议：MQTT
	public static final String ASSET_MODEL_PROTOCOL_SZY		= "SZY";				//设备协议：SZY
	
	public static final String ASSET_CMD_STATUS_INIT			= "init";			//未执行
	public static final String ASSET_CMD_STATUS_SENT			= "sent";			//已发送，未反馈
	public static final String ASSET_CMD_STATUS_SUCCESS			= "success";		//已反馈，执行成功
	public static final String ASSET_CMD_STATUS_FAIL			= "fail";			//已反馈，执行失败
	public static final String ASSET_CMD_STATUS_CANCEL			= "cancel";			//取消执行
	
	
	public static final String ANA_CA_TP_CAL				= "cal";				//相关性分析：计算值
	public static final String ANA_CA_TP_MANUAL				= "manual";				//相关性分析：手工指定
	
	public static final String ANA_TASK_STATUS_INIT           = "init";           //未执行
    public static final String ANA_TASK_STATUS_SUCCESS        = "success";           //已执行
    
    public final static String DATA_TAG_EXCP_ID = "id";
    public final static String DATA_TAG_EXCP_TM = "tm";	
    public final static String DATA_TAG_EXCP_DATA = "data";
}
