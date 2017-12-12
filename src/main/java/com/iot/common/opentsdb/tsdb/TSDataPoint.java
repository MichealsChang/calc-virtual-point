package com.iot.common.opentsdb.tsdb;


public class TSDataPoint implements Comparable<TSDataPoint> {
	private long timestamp = 0;
	private double value = 0.0;

	public TSDataPoint(long timestap, double value) {
//		this.timestamp = removeMS(timestap);
		this.timestamp = timestap;
		this.value = value;
	}

	public long getTimestamp() {
//		return addMS(timestamp);
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
//		this.timestamp = removeMS(timestamp);
		this.timestamp = timestamp;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStrValue() {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
	    nf.setGroupingUsed(false);
		return nf.format(this.value);
	}

	@Override
	public int compareTo(TSDataPoint o) {
		return this.getTimestamp() > o.getTimestamp() ? 1 : -1;
	}
	
	private long removeMS(long l) {
		if (l >= 10000000000l) {
			l = l / 1000;
		}
		return l;
	}
	
	private long addMS(long l) {
		if (l < 10000000000l) {
			l = l * 1000;
		}
		return l;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TSDataPoint) {
			TSDataPoint other = (TSDataPoint)obj;
			if (other.getValue() == this.getValue() && other.getTimestamp() == this.getTimestamp()) {
				return true;
			}
		}
		
		return false;
	}
	
	
}
