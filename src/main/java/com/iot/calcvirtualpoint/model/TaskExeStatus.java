package com.iot.calcvirtualpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaskExeStatus implements Serializable {

	private static final long serialVersionUID = -6629196062530913421L;

	private Long id;
	private String taskId;
	private String status;
	private String lastExeTime;
	private int lastTimeConsuming;
	private String createTime;
	private String remarks;

	public TaskExeStatus() {

	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("taskId", this.taskId);
		map.put("status", this.status);
		map.put("lastExeTime", this.lastExeTime);
		map.put("lastTimeConsuming", this.lastTimeConsuming);
		map.put("createTime", this.createTime);
		map.put("remarks", this.remarks);
		return map;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastExeTime() {
		return lastExeTime;
	}

	public void setLastExeTime(String lastExeTime) {
		this.lastExeTime = lastExeTime;
	}

	public int getLastTimeConsuming() {
		return lastTimeConsuming;
	}

	public void setLastTimeConsuming(int lastTimeConsuming) {
		this.lastTimeConsuming = lastTimeConsuming;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
