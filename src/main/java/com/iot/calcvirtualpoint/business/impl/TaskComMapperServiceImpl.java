package com.iot.calcvirtualpoint.business.impl;

import java.util.List;

import com.exue.framework.entity.BaseList;
import com.iot.calcvirtualpoint.business.inf.ITaskComMapperService;
import com.iot.calcvirtualpoint.dao.ITaskComMapperDao;
import com.iot.calcvirtualpoint.model.TaskComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskComMapperServiceImpl implements ITaskComMapperService {

	@Autowired
	private ITaskComMapperDao taskComMapperDao;
	
	@Override
	public BaseList<TaskComMapper> findList(TaskComMapper t) {
		BaseList<TaskComMapper> result = null;
		if (t == null) {
			return result;
		}
		result = new BaseList<TaskComMapper>();
		result.setTotalRows(taskComMapperDao.count(t.toMap()));
		if (result.getTotalRows() > 0 ) {
			result.setList(taskComMapperDao.findList(t.toMap()));
		}
		return result;
	}

	@Override
	public List<TaskComMapper> findListByTaskId(TaskComMapper t) {
		List<TaskComMapper> result = null;
		if (t == null) {
			return result;
		}
		result = taskComMapperDao.findListByTaskId(t.toMap());
		return result;
	}

}
