package com.iot.calcvirtualpoint.dao;

import java.util.ArrayList;
import java.util.Map;

public interface ISuperDao<T extends java.io.Serializable> {

	int save(T t);

	int save(Map<String, Object> params);

	int deleteById(Long id);

	int delete(Map<String, Object> params);

	int update(T t);

	int update(Map<String, Object> params);

	T getById(Long id);

	ArrayList<T> findList(T t);

	ArrayList<T> findList(Map<String, Object> params);

	ArrayList<T> findList();

	int count();

	int count(Map<String, Object> params);

	int count(T t);

}
