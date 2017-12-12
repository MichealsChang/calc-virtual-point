package com.iot.calcvirtualpoint.business.impl;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.business.inf.ITaskCalculateService;
import com.iot.calcvirtualpoint.dao.ITaskCalculateDao;
import com.iot.calcvirtualpoint.model.TaskCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCalculateServiceImpl implements ITaskCalculateService {

	@Autowired
	private ITaskCalculateDao taskCalculateDao;

	@Override
	public BaseList<TaskCalculate> findList(TaskCalculate t) {
		BaseList<TaskCalculate> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskCalculate>();
		result.setTotalRows(taskCalculateDao.count(t.toMap()));
		if (result.getTotalRows() > 0) {
			result.setList(taskCalculateDao.findList(t.toMap()));
		}
		return result;
	}

	@Override
	public TaskCalculate getById(Long id) {
		TaskCalculate result = null;
		if (id == null) {
			return result;
		}
		result = taskCalculateDao.getById(id);
		return result;
	}

}
