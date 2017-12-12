package com.iot.calcvirtualpoint.common.db;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts(value = {
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = {
				MappedStatement.class, Object.class }) })

public class RWSplitting implements Interceptor {

	JdbcContextHolder jdbcContextHolder;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

		if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
			System.out.println("read!~~");
			jdbcContextHolder.setSlave();
		} else {
			if (null == jdbcContextHolder.getJdbcType()) {
				System.out.println("write!~~");
				jdbcContextHolder.setMaster();
			}
		}
		try {
			return invocation.proceed();
		} finally {
			jdbcContextHolder.clearJdbcType();
		}
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		System.out.println("set properties!~~");
		System.out.println(arg0);
	}

	public void setJdbcContextHolder(JdbcContextHolder jdbcContextHolder) {
		this.jdbcContextHolder = jdbcContextHolder;
	}

}
