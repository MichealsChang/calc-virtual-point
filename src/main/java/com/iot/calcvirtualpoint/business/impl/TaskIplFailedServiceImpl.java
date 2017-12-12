package com.iot.calcvirtualpoint.business.impl;

import com.iot.calcvirtualpoint.business.inf.ITaskIplFailedService;
import com.iot.calcvirtualpoint.dao.ITaskIplFailedDao;
import com.iot.calcvirtualpoint.model.TaskIplFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exue.framework.constant.RespStatus;
import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;

@Service
public class TaskIplFailedServiceImpl implements ITaskIplFailedService {

	@Autowired
	private ITaskIplFailedDao taskIplFailedDao;

	@Override
	public BaseResp insert(TaskIplFailed t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		int r = taskIplFailedDao.save(t);
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

	@Override
	public BaseList<TaskIplFailed> findList(TaskIplFailed t) {
		BaseList<TaskIplFailed> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskIplFailed>();
		result.setTotalRows(taskIplFailedDao.count(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskIplFailedDao.findList(t.toMap()));
		}
		return result;
	}

	@Override
	public boolean deleteById(Long id) {
		boolean result = false;
		if (id == null) {
			return result;
		}
		int delRes = taskIplFailedDao.deleteById(id);
		if (delRes > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public BaseList<TaskIplFailed> findListByCond(TaskIplFailed t) {
		BaseList<TaskIplFailed> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskIplFailed>();
		result.setTotalRows(taskIplFailedDao.countByCond(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskIplFailedDao.findListByCond(t.toMap()));
		}
		return result;
	}

	@Override
	public BaseList<TaskIplFailed> findListByDay(TaskIplFailed t) {
		BaseList<TaskIplFailed> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskIplFailed>();
		result.setTotalRows(taskIplFailedDao.countByCond(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskIplFailedDao.findListByCond(t.toMap()));
		}
		return result;
	}

}
