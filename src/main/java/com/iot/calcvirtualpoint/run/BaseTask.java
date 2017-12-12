package com.iot.calcvirtualpoint.run;

import com.iot.calcvirtualpoint.common.util.LogUtils;

import java.util.Date;

public abstract class BaseTask implements Runnable {

	private String taskId;

	public abstract void handle() throws Exception;

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		LogUtils.debugLog("-------------------------begin handle：startTime:" + startTime + ",now:" + new Date());
		try {
			handle();
		} catch (RuntimeException e) {
			LogUtils.syndataerrorLog("线程执行异常！\n\r 错误：" + e.getMessage());
		} catch (Exception e) {
			LogUtils.syndataerrorLog("线程执行异常！\n\r 错误：" + e.getMessage());
		} finally {

		}
		long endTime = System.currentTimeMillis();
		long pTime = (endTime - startTime) / 1000;
		LogUtils.debugLog("-------------------------end handle：endTime:" + endTime + ",now:" + new Date());
		LogUtils.debugLog("-------------------------end handle：pTime:" + pTime + ",now:" + new Date());
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

}
