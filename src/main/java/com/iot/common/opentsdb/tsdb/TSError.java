package com.iot.common.opentsdb.tsdb;

public class TSError {
	private String message = "";
	private String trace = "";
	
	public TSError(String message) {
		this.message = message;
	}
	
	public TSError(String message, String trace) {
		this.message = message;
		this.trace = trace;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

}
