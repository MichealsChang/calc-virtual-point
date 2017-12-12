package com.iot.calcvirtualpoint.business.impl;

import com.iot.calcvirtualpoint.business.inf.ITaskIplDetailService;
import com.iot.calcvirtualpoint.dao.ITaskIplDetailDao;
import com.iot.calcvirtualpoint.model.TaskIplDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exue.framework.constant.RespStatus;
import com.exue.framework.entity.BaseResp;

@Service
public class TaskIplDetailServiceImpl implements ITaskIplDetailService {

	@Autowired
	private ITaskIplDetailDao taskIplDetailDao;
	
	@Override
	public BaseResp insert(TaskIplDetail t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		int r = taskIplDetailDao.save(t.toTaskIplDetail());
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

}
