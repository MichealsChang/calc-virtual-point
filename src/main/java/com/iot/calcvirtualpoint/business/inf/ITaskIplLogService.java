package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;
import com.iot.calcvirtualpoint.model.TaskIplLog;

public interface ITaskIplLogService {

	/**
	 * 新增虚拟清算日志主表
	 * @param t
	 * @return
	 */
	BaseResp insert(TaskIplLog t);
	
	/**
	 * 更新虚拟清算日志主表
	 * @param t
	 * @return
	 */
	BaseResp update(TaskIplLog t);
	
	/**
	 * 获取虚拟清算日志主表列表
	 * @param t
	 * @return
	 */
	BaseList<TaskIplLog> findList(TaskIplLog t);
	
	/**
	 * 删除虚拟清算日志主表
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	
}
