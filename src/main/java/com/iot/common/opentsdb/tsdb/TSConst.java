package com.iot.common.opentsdb.tsdb;

public class TSConst {	
	public static final String METRIC_IOT_READING = "iot.reading";
	
	public static final int MAX_DATAPOINT_NUM_PER_REQ = 50;	//maximum number of data points per request
	
	public static final String TAG_DATA_TYPE = "dtp";

	public static final String START_TIME = "start";
	public static final String END_TIME = "end";
	public static final String TAG_DID = "metric";
	public static final String TAG_MID = "key";
}
