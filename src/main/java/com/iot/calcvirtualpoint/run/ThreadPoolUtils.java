package com.iot.calcvirtualpoint.run;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolUtils {

	private ExecutorService executorService;

	public ThreadPoolUtils() {

	}

	public ExecutorService createExecutor(int POOL_SIZE) {
		if (executorService != null) {
			return executorService;
		} else {
			LogUtils.debugLog("ThreadPoolUtils=== Runtime.getRuntime().availableProcessors()==:" + Runtime.getRuntime().availableProcessors() + ",POOL_SIZE:" + POOL_SIZE);
			executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
			return executorService;
		}
	}

	public void close() {

		try {
			long beginTime = System.currentTimeMillis();
			LogUtils.debugLog("[ThreadPool] is shutting down /r/n");
			// 请求关闭线程池
			// 线程池不再接收新的任务, 但是会继续执行完工作队列中现有的任务
			executorService.shutdown();
			LogUtils.debugLog("[ThreadPoolUtils] executorService.shutdown()");
			// 等待关闭线程池, 每次等待的超时时间为 THREADPOLL_COLSE_WAITTIME 秒
			while (!executorService.isTerminated()) {
				executorService.awaitTermination(Configuration.THREADPOLL_COLSE_WAITTIME, TimeUnit.SECONDS);
				LogUtils.debugLog("[ThreadPoolUtils] executorService.awaitTermination()");
			}
			LogUtils.debugLog("[ThreadPoolUtils] executorService.isTerminated()");
			LogUtils.debugLog("[ThreadPoolUtils] executorService.close()");

			long endTime = System.currentTimeMillis();
			LogUtils.debugLog("[ThreadPoolUtils] 已经关闭,所用的时间:" + (endTime - beginTime) + "毫秒/r/n");

		} catch (Exception e) {
			LogUtils.debugLog("[ThreadPoolUtils] exception: " + e.getMessage());
			LogUtils.error(e.getMessage(), e);
		}
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

}
