package com.iot.calcvirtualpoint.model;

import java.io.Serializable;

public class DataPoint implements Serializable {

	private static final long serialVersionUID = -440188381519864242L;

	private String key;
	private long timestamp;
	private String[] values;

	public DataPoint() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
}
