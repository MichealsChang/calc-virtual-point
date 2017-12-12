package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;
import com.iot.calcvirtualpoint.model.TaskExeStatus;

public interface ITaskExeStatusService {

	/**
	 * 新增任务执行状态
	 * @param t
	 * @return
	 */
	BaseResp insert(TaskExeStatus t);

	/**
	 * 获取任务执行状态
	 * @param id
	 * @return
	 */
	TaskExeStatus getById(Long id);

	/**
	 * 更新任务执行状态
	 * @param t
	 * @return
	 */
	BaseResp update(TaskExeStatus t);
	
	/**
	 * 获取任务执行状态信息列表
	 * @param t
	 * @return
	 */
	BaseList<TaskExeStatus> findList(TaskExeStatus t);

}
