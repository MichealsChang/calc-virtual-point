package com.iot.calcvirtualpoint.business.inf;

import com.exue.framework.entity.BaseResp;
import com.iot.calcvirtualpoint.model.TaskIplDetail;

public interface ITaskIplDetailService {

	/**
	 * 新增日志详细
	 * @param t
	 * @return
	 */
	BaseResp insert(TaskIplDetail t);
	
}
