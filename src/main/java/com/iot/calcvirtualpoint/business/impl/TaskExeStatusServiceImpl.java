package com.iot.calcvirtualpoint.business.impl;

import com.iot.calcvirtualpoint.business.inf.ITaskExeStatusService;
import com.iot.calcvirtualpoint.dao.ITaskExeStatusDao;
import com.iot.calcvirtualpoint.model.TaskExeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exue.framework.constant.RespStatus;
import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;

@Service
public class TaskExeStatusServiceImpl implements ITaskExeStatusService {

	@Autowired
	private ITaskExeStatusDao taskExeStatusDao;

	@Override
	public BaseResp insert(TaskExeStatus t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		int r = taskExeStatusDao.save(t);
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

	@Override
	public TaskExeStatus getById(Long id) {
		TaskExeStatus result = null;
		if (id == null) {
			return result;
		}
		result = taskExeStatusDao.getById(id);
		return result;
	}

	@Override
	public BaseResp update(TaskExeStatus t) {
		BaseResp result = new BaseResp();
		if (t == null) {
			return result.putStatus(RespStatus.PARAMS_VALID_ERROR);
		}
		TaskExeStatus tes = taskExeStatusDao.getById(t.getId());
		if (tes == null) {
			result.putStatus(RespStatus.RECORD_NOT_FOUND);
			return result;
		}
		int r = taskExeStatusDao.update(t);
		if (r > 0) {
			result.success();
		} else {
			result.error();
		}
		return result;
	}

	@Override
	public BaseList<TaskExeStatus> findList(TaskExeStatus t) {
		BaseList<TaskExeStatus> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskExeStatus>();
		result.setTotalRows(taskExeStatusDao.count(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskExeStatusDao.findList(t.toMap()));
		}
		return result;
	}

}
