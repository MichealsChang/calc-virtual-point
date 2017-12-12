package com.iot.calcvirtualpoint.business.impl;

import com.iot.calcvirtualpoint.business.inf.ITaskIplLogService;
import com.iot.calcvirtualpoint.dao.ITaskIplLogDao;
import com.iot.calcvirtualpoint.model.TaskIplLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exue.framework.constant.RespStatus;
import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;

@Service
public class TaskIplLogServiceImpl implements ITaskIplLogService {

	@Autowired
	private ITaskIplLogDao taskIplLogDao;

	@Override
	public BaseResp insert(TaskIplLog t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		int r = taskIplLogDao.save(t);
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

	@Override
	public BaseResp update(TaskIplLog t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		TaskIplLog tes = taskIplLogDao.getById(t.getId());
		if (tes == null) {
			result.putStatus(RespStatus.RECORD_NOT_FOUND);
			return result;
		}
		int r = taskIplLogDao.update(t);
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

	@Override
	public BaseList<TaskIplLog> findList(TaskIplLog t) {
		BaseList<TaskIplLog> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskIplLog>();
		result.setTotalRows(taskIplLogDao.count(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskIplLogDao.findList(t.toMap()));
		}
		return result;
	}

	@Override
	public boolean deleteById(Long id) {
		boolean result = false;
		if (id == null) {
			return result;
		}
		int delRes = taskIplLogDao.deleteById(id);
		if (delRes > 0) {
			result = true;
		}
		return result;
	}

}
