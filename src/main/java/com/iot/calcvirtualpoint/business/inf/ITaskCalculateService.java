package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.model.TaskCalculate;

public interface ITaskCalculateService {

	/**
	 * 获取任务计算公式列表
	 * @param t
	 * @return
	 */
	BaseList<TaskCalculate> findList(TaskCalculate t);

	/**
	 * 通过主键获取任务计算公式
	 * @param id
	 * @return
	 */
	TaskCalculate getById(Long id);

}
