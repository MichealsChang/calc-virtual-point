package com.iot.calcvirtualpoint.run;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.business.inf.ITaskCalculateService;
import com.iot.calcvirtualpoint.business.inf.ITaskComMapperService;
import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.runtime.ApplicationContextHolder;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import com.iot.calcvirtualpoint.model.TaskCalculate;
import com.iot.calcvirtualpoint.model.TaskComMapper;
import org.springframework.stereotype.Component;

import com.exue.framework.util.DateUtils;

@Component
public class IplTaskServer {

	public IplTaskServer() {

	}

	private ITaskComMapperService taskComMapperService = ApplicationContextHolder.getBean("taskComMapperServiceImpl");
	private ITaskCalculateService taskCalculateService = ApplicationContextHolder.getBean("taskCalculateServiceImpl");

	// 线程任务集合
	private static ConcurrentHashMap<String, Object> taskMap = new ConcurrentHashMap<String, Object>();

	@PostConstruct
	public void init() {
		LogUtils.debugLog("SynTaskServer=== init =========:" + DateUtils.getWsTime() + ",treadId:" + Thread.currentThread().getId());
		ThreadPoolUtils threadPoolUtils = (ThreadPoolUtils) ApplicationContextHolder.getBean("threadPoolUtils");
		ScheduledExecutorService executorService = (ScheduledExecutorService) threadPoolUtils.createExecutor(Configuration.LJTH_DATASYN_THREADPOLL_SIZE);

		TaskComMapper t = new TaskComMapper();
		List<TaskComMapper> baseList = taskComMapperService.findListByTaskId(t);
		int count = 0;
		if (baseList.size() > 0) {
			for (TaskComMapper tc : baseList) {
				String taskId = tc.getTaskId();
				IplTask iplTask = new IplTask(taskId);

				Map<TaskComMapper, List<TaskCalculate>> taskComMap = new HashMap<TaskComMapper, List<TaskCalculate>>();

				t.setTaskId(taskId);
				BaseList<TaskComMapper> list = taskComMapperService.findList(t);
				if (list.getTotalRows() > 0) {
					for (TaskComMapper tcm : list.getList()) {
						TaskCalculate taskCal = new TaskCalculate();
						taskCal.setConsId(tcm.getConsId());
						BaseList<TaskCalculate> taskCalcList = taskCalculateService.findList(taskCal);
						if (taskCalcList.getTotalRows() > 0) {
							taskComMap.put(tcm, taskCalcList.getList());
						}
					}
					iplTask.setTaskComMap(taskComMap);
				}
				taskMap.put(taskId, iplTask);
				executorService.scheduleAtFixedRate(iplTask, 1000 * count, Configuration.TASK_EXE_FREQUENCY, TimeUnit.MILLISECONDS);
				count++;
			}
		}

	}

	@PreDestroy
	public void destory() {
		ThreadPoolUtils threadPoolUtils = (ThreadPoolUtils) ApplicationContextHolder.getBean("threadPoolUtils");
		threadPoolUtils.close();
		LogUtils.debugLog("SynTaskServer=== destory =========:" + DateUtils.getWsTime() + ",treadId:" + Thread.currentThread().getId());

	}

	public static ConcurrentHashMap<String, Object> getTaskMap() {
		return taskMap;
	}

	public static void setTaskMap(ConcurrentHashMap<String, Object> taskMap) {
		IplTaskServer.taskMap = taskMap;
	}

}
