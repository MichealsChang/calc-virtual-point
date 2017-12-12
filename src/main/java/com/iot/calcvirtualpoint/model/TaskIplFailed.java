package com.iot.calcvirtualpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaskIplFailed implements Serializable {

	private static final long serialVersionUID = 6879221925380200708L;

	private Long id;
	private Long calcId;
	private String begTime;
	private String endTime;
	private String createTime;
	private String remarks;

	public TaskIplFailed() {

	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("calcId", this.calcId);
		map.put("begTime", this.begTime);
		map.put("endTime", this.endTime);
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

	public Long getCalcId() {
		return calcId;
	}

	public void setCalcId(Long calcId) {
		this.calcId = calcId;
	}

	public String getBegTime() {
		return begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
