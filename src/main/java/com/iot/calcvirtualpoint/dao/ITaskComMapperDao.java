package com.iot.calcvirtualpoint.dao;

import com.iot.calcvirtualpoint.model.TaskComMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITaskComMapperDao extends ISuperDao<TaskComMapper> {

    List<TaskComMapper> findListByTaskId(Map<String, Object> params);

}
