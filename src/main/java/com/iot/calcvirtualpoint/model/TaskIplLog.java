package com.iot.calcvirtualpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaskIplLog implements Serializable {

	private static final long serialVersionUID = 2420395973745071835L;

	private Long id;
	private String taskId;
	private String consId;
	private String finishTime;
	private String begTime;
	private String endTime;
	private int exeConsuming;
	private String exeResult;
	private int sucessNum;
	private int failNum;
	private String status;
	private String createTime;
	private String remarks;

	public TaskIplLog() {

	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("taskId", this.taskId);
		map.put("consId", this.consId);
		map.put("finishTime", this.finishTime);
		map.put("begTime", this.begTime);
		map.put("endTime", this.endTime);
		map.put("exeConsuming", this.exeConsuming);
		map.put("exeResult", this.exeResult);
		map.put("sucessNum", this.sucessNum);
		map.put("failNum", this.failNum);
		map.put("status", this.status);
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

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
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

	public int getExeConsuming() {
		return exeConsuming;
	}

	public void setExeConsuming(int exeConsuming) {
		this.exeConsuming = exeConsuming;
	}

	public String getExeResult() {
		return exeResult;
	}

	public void setExeResult(String exeResult) {
		this.exeResult = exeResult;
	}

	public int getSucessNum() {
		return sucessNum;
	}

	public void setSucessNum(int sucessNum) {
		this.sucessNum = sucessNum;
	}

	public int getFailNum() {
		return failNum;
	}

	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
