package com.iot.calcvirtualpoint.dao;

import com.iot.calcvirtualpoint.model.TaskIplFailed;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface ITaskIplFailedDao extends ISuperDao<TaskIplFailed> {

	int countByCond(Map<String, Object> params);
	
	ArrayList<TaskIplFailed> findListByCond(Map<String, Object> params);
	
	int countByDay(Map<String, Object> params);
	
	ArrayList<TaskIplFailed> findListByDay(Map<String, Object> params);
	
}
