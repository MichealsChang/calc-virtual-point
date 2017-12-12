package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.model.TaskComMapper;

import java.util.List;

public interface ITaskComMapperService {

	/**
	 * 获取任务企业映射信息列表
	 * @param t
	 * @return
	 */
	BaseList<TaskComMapper> findList(TaskComMapper t);
	
	/**
	 * 通过taskid获取任务企业映射信息列表
	 * @param t
	 * @return
	 */
	List<TaskComMapper> findListByTaskId(TaskComMapper t);
	
}
