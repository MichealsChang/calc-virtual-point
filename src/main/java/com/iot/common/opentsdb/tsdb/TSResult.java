package com.iot.common.opentsdb.tsdb;

import java.util.ArrayList;
import java.util.List;

public class TSResult {
	private List<TSMetricData> data = new ArrayList<TSMetricData>();
	private String host;
	private int port;

	public List<TSMetricData> getData() {
		return data;
	}
	public void setData(List<TSMetricData> data) {
		this.data = data;
	}
	//--------------------------------------------
	
	private List<TSError> errors = new ArrayList<TSError>();

	public boolean hasError() {
		return errors.size() > 0;
	}
	
	public List<TSError> getErrors() {
		return errors;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
