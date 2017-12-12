package com.iot.calcvirtualpoint.quartz;

import com.iot.calcvirtualpoint.common.util.DateTimeUtil;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import com.iot.calcvirtualpoint.run.IplTask;
import com.iot.calcvirtualpoint.run.IplTaskServer;

import java.util.concurrent.ConcurrentHashMap;

public class ClearMapJob {

	public void clearMap() {
		LogUtils.debugLog("========================= start clear map ... ============================");
		ConcurrentHashMap<String, Object> taskMap = IplTaskServer.getTaskMap();
		for (Object task : taskMap.values()) {
			ConcurrentHashMap<String, Integer> map = ((IplTask) task).getIsQuery();
			for (String key : map.keySet()) {
				String dateString = key.substring(key.lastIndexOf("_") + 1, key.length());
				long mill = DateTimeUtil.parse(dateString, "yyyy/MM/dd HH:mm:ss").getTime();
				if (System.currentTimeMillis() - mill >= 20 * 60 * 1000) {
					LogUtils.debugLog("map.remove(" + key + ")");
					map.remove(key);
				}
			}
		}
		LogUtils.debugLog("========================== end clear map ! ================================");
	}

}
