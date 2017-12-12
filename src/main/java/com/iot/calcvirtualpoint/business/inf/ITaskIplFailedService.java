package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;
import com.iot.calcvirtualpoint.model.TaskIplFailed;

public interface ITaskIplFailedService {

	/**
	 * 新增虚拟清算失败的记录
	 * @param t
	 * @return
	 */
	BaseResp insert(TaskIplFailed t);
	
	/**
	 * 获取虚拟清算失败的记录列表
	 * @param t
	 * @return
	 */
	BaseList<TaskIplFailed> findList(TaskIplFailed t);
	
	/**
	 * 获取昨天虚拟清算失败的记录列表
	 * @param t
	 * @return
	 */
	BaseList<TaskIplFailed> findListByCond(TaskIplFailed t);
	
	/**
	 * 查询当天的数据
	 * @param t
	 * @return
	 */
	BaseList<TaskIplFailed> findListByDay(TaskIplFailed t);
	
	/**
	 * 删除虚拟清算失败的记录
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	
}
