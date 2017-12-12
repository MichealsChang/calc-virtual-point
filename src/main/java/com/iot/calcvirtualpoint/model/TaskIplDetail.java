package com.iot.calcvirtualpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaskIplDetail implements Serializable {

	private static final long serialVersionUID = 4966917960305844407L;

	private Long id;
	private Long logId;
	private String time;
	private String msId;
	private String createTime;
	private String tableName;

	public TaskIplDetail() {

	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("logId", this.logId);
		map.put("time", this.time);
		map.put("msId", this.msId);
		map.put("createTime", this.createTime);
		return map;
	}
	
	public TaskIplDetail toTaskIplDetail(){
		TaskIplDetail detail =  new TaskIplDetail();
		detail.setId(this.id);
		detail.setLogId(this.logId);
		detail.setMsId(this.msId);
		detail.setTime(this.time);
		detail.setCreateTime(this.createTime);
		detail.setTableName(this.tableName);
		return detail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMsId() {
		return msId;
	}

	public void setMsId(String msId) {
		this.msId = msId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
