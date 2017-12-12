package com.iot.common.opentsdb.tsdb;

import com.iot.common.opentsdb.util.IoTConst;

public class TSQuery {
	public static final String DEFALT_START_TIME = "1d-ago";
	public static final String DEFALT_END_TIME = null;
	
	private String aggregator = IoTConst.DATA_CAL_FUNC_SUM;
	private String downsampler = "";
	
	private String start = DEFALT_START_TIME;
	private String end = DEFALT_END_TIME;

	private String metric = "";
	private String key = "";

	public TSQuery() {
		
	}
	public void setAggregator(String aggregator) {
		this.aggregator = aggregator;
	}

	public String getAggregator() {
		return aggregator;
	}

	public String getDownsampler() {
		return downsampler;
	}

	public void setDownsampler(String downsampler) {
		this.downsampler = downsampler;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMetric() {
		return metric;
	}
}
