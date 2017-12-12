package com.iot.calcvirtualpoint.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.business.inf.ITaskCalculateService;
import com.iot.calcvirtualpoint.business.inf.ITaskComMapperService;
import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.runtime.ApplicationContextHolder;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import com.iot.calcvirtualpoint.model.TaskCalculate;
import com.iot.calcvirtualpoint.model.TaskComMapper;
import com.iot.calcvirtualpoint.run.IplTask;
import com.iot.calcvirtualpoint.run.IplTaskServer;
import com.iot.calcvirtualpoint.run.ThreadPoolUtils;

public class RefreshTaskJob {

	private ITaskComMapperService taskComMapperService = ApplicationContextHolder.getBean("taskComMapperServiceImpl");
	private ITaskCalculateService taskCalculateService = ApplicationContextHolder.getBean("taskCalculateServiceImpl");

	ThreadPoolUtils threadPoolUtils = (ThreadPoolUtils) ApplicationContextHolder.getBean("threadPoolUtils");
	ScheduledExecutorService executorService = (ScheduledExecutorService) threadPoolUtils.createExecutor(Configuration.LJTH_DATASYN_THREADPOLL_SIZE);

	public void refreshTask() {
		LogUtils.debugLog("==================================开始刷新任务...===========================================");
		ConcurrentHashMap<String, Object> taskMap = IplTaskServer.getTaskMap();
		TaskComMapper t = new TaskComMapper();
		List<TaskComMapper> baseList = taskComMapperService.findListByTaskId(t);
		if (baseList.size() > 0) {
			for (TaskComMapper tc : baseList) {
				String taskId = tc.getTaskId();

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
				}
				
				if(taskMap.containsKey(taskId)){
					IplTask task = (IplTask)taskMap.get(taskId);
					task.setTaskComMap(taskComMap);
				}else{
					IplTask task = new IplTask(taskId);
					task.setTaskComMap(taskComMap);
					taskMap.put(taskId, task);
					executorService.scheduleAtFixedRate(task, 1000, Configuration.TASK_EXE_FREQUENCY, TimeUnit.MILLISECONDS);
				}
			}
		}
		LogUtils.debugLog("=====================================结束刷新任务！================================================");
	}

}
