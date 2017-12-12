package com.iot.calcvirtualpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaskComMapper implements Serializable {

	private static final long serialVersionUID = -1757807875666796212L;

	private Long id;
	private String taskId;
	private String consId;
	private String msId;
	private int timeDif;
	private String createTime;
	private String remarks;

	public TaskComMapper() {

	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("taskId", this.taskId);
		map.put("consId", this.consId);
		map.put("msId", this.msId);
		map.put("timeDif", this.timeDif);
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

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId;
	}

	public String getMsId() {
		return msId;
	}

	public void setMsId(String msId) {
		this.msId = msId;
	}

	public int getTimeDif() {
		return timeDif;
	}

	public void setTimeDif(int timeDif) {
		this.timeDif = timeDif;
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
