package com.mybatis.learn.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

// @Component
@Intercepts({@Signature(
		type = Executor.class,
		method = "select",
		args = {MappedStatement.class, Object.class})})
public class ExecutorPlugin implements Interceptor {

	private Properties properties = new Properties();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("前置处理");
		Object returnObj = invocation.proceed();
		System.out.println("后置处理");
		return returnObj;
	}

	@Override
	public Object plugin(Object target) {
		return null;
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
