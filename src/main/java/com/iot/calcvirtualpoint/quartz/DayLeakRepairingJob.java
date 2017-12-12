package com.iot.calcvirtualpoint.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.exue.framework.entity.BaseList;
import com.ibm.icu.text.SimpleDateFormat;
import com.iot.calcvirtualpoint.business.inf.ITaskCalculateService;
import com.iot.calcvirtualpoint.business.inf.ITaskIplFailedService;
import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.runtime.ApplicationContextHolder;
import com.iot.calcvirtualpoint.common.util.DateTimeUtil;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import com.iot.calcvirtualpoint.model.DataPoint;
import com.iot.calcvirtualpoint.model.TaskCalculate;
import com.iot.calcvirtualpoint.model.TaskIplFailed;
import com.iot.calcvirtualpoint.util.FormulaCalculator;
import com.iot.calcvirtualpoint.util.KafkaUtil;
import com.iot.calcvirtualpoint.util.Tools;
import com.iot.common.opentsdb.tsdb.*;

public class DayLeakRepairingJob {

	private ITaskIplFailedService taskIplFailedService = ApplicationContextHolder.getBean("taskIplFailedServiceImpl");
	private ITaskCalculateService taskCalculateService = ApplicationContextHolder.getBean("taskCalculateServiceImpl");

	// 获取tsdb连接
	TSClient client = new TSClient(Configuration.TSDB_IP, Configuration.TSDB_PORT);

	@SuppressWarnings("deprecation")
	public void dayLeakRepairing() {
		LogUtils.debugLog("************************* 日终补漏开始 ... *************************");
		TaskIplFailed failed = new TaskIplFailed();
		BaseList<TaskIplFailed> baseList = taskIplFailedService.findListByCond(failed);
		if (baseList.getTotalRows() > 0) {

			// 获取MQ连接
			boolean conn = KafkaUtil.open(Configuration.KAFKA_BROKERS, Configuration.KAFKA_TOPIC);
			LogUtils.debugLog("日终补漏 | 获取MQ连接是否成功：" + conn);
			if (conn) {
				int level = -1;
				for (TaskIplFailed fail : baseList.getList()) {
					try {

						String startTime = fail.getBegTime();
						String endTime = fail.getEndTime();

						Date endDate = DateTimeUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
						String tm = DateTimeUtil.getLast15MinutesDateByDate(DateTimeUtil.format(endDate, "yyyy/MM/dd HH:mm:ss"));
						// 默认值
						long timestamp = DateTimeUtil.parseDatetime(tm, "yyyy/MM/dd HH:mm:ss").getTime();

						TaskCalculate tc = taskCalculateService.getById(fail.getCalcId());

						if (level == -1) {
							level = tc.getCalcLevel();
						} else {
							if (level != tc.getCalcLevel()) {
								LogUtils.debugLog("日终补漏 | 将要计算下一个计算级别，程序休眠 " + (Configuration.CALC_WAIT_TIME / 1000) + " s...");
								Thread.sleep(Configuration.CALC_WAIT_TIME);
								LogUtils.debugLog("日终补漏 | ********************************************************************");
								level = tc.getCalcLevel();
							}
						}

						String formula = tc.getCalcFormula();
						LogUtils.debugLog("日终补漏 | 计算公式：" + formula);
						Map<Integer, Object> params = new TreeMap<Integer, Object>();
						Map<Integer, String> values = new TreeMap<Integer, String>();
						// 循环获取都有哪些参数
						for (int i = 1; i < 53; i++) {
							// 通过get方法的反射，获取参数的值
							Object param = Tools.getValueByGetMethodName(tc, i);
							if (param != null && !"".trim().equals(param)) {
								// 有值的参数加入map集合，以便以后的数据获取...
								params.put(i, param);
							}
							if (param == null) {
								break;
							}
						}
						LogUtils.debugLog("日终补漏 | 参数个数：" + params.size());

						// 查询实点数据
						for (Map.Entry<Integer, Object> en : params.entrySet()) {
							TSQuery q = new TSQuery();
							q.setAggregator("none");
							q.setKey(en.getValue().toString());
							q.setMetric(tc.getDataItemCode());
							q.setStart(DateTimeUtil.format(DateTimeUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss"));
							q.setEnd(DateTimeUtil.format(DateTimeUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss"));
							long start = System.currentTimeMillis();
							TSResult r = client.readAllData(q);
							long end = System.currentTimeMillis();
							LogUtils.debugLog("日终补漏 | query|" + JSONObject.toJSONString(q) + "|耗时：" + (end - start) + " ms...");
							if (r != null) {
								if (r.hasError()) {
									LogUtils.debugLog("日终补漏 | read from " + r.getHost() + ":" + r.getPort() + " failure!");
									for (TSError error : r.getErrors()) {
										LogUtils.debugLog("日终补漏 | " + error.getMessage());
									}
								} else {
									LogUtils.debugLog("日终补漏 | read from " + r.getHost() + ":" + r.getPort() + " success!");
									List<TSMetricData> list = r.getData();
									for (TSMetricData d : list) {
										List<TSDataPoint> dps = d.getDps();
										// 读数成功，下次不再重新计算，否则，加入失败map中，下次重新计算
										if (dps.size() > 0) {
											for (TSDataPoint dp : dps) {
												Date dat = new Date(dp.getTimestamp());
												if (dat.getMinutes() % 15 == 0) {
													values.put(en.getKey(), dp.getStrValue());
													timestamp = dp.getTimestamp();
													LogUtils.debugLog("日终补漏 | " + "tm:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dp.getTimestamp()) + ", " + "value:" + dp.getValue());
												}
											}
										} else {
											values.put(en.getKey(), "0");
										}
									}
								}
							}
						}
						LogUtils.debugLog("日终补漏 | 从TSDB中获取值的个数：" + values.size());

						// 根据公式进行计算
						for (Map.Entry<Integer, String> entryV : values.entrySet()) {
							formula = formula.replaceFirst("P" + entryV.getKey(), entryV.getValue());
						}
						LogUtils.debugLog("日终补漏 | 参数处理过后的计算公式：" + formula);

						double realValue = FormulaCalculator.getResult(formula);
						LogUtils.debugLog("日终补漏 | 最终计算结果值：" + realValue);

						// 写入MQ
						DataPoint data = new DataPoint();

						data.setKey(tc.getMsId());
						data.setTimestamp(timestamp);
						data.setValues(new String[]{tc.getDataItemCode()+"="+realValue});

						String msg = JSONObject.toJSONString(data).toUpperCase();
						LogUtils.debugLog("日终补漏  | 将要写入MQ中的消息：" + msg);
//						boolean success = MQClient.send(msg);
//						if (success) {
//							LogUtils.debugLog("日终补漏 | 写入MQ成功...");
//							taskIplFailedService.deleteById(fail.getId());
//							LogUtils.debugLog("日终补漏 | 删除补漏成功的数据...");
//						} else {
//							LogUtils.debugLog("日终补漏 | 写入MQ失败...");
//						}

						KafkaUtil.sendMessage(msg);
						LogUtils.debugLog("日终补漏 | 写入MQ成功...");
						taskIplFailedService.deleteById(fail.getId());
						LogUtils.debugLog("日终补漏 | 删除补漏成功的数据...");

						LogUtils.debugLog("日终补漏 | +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					} catch (Exception e) {
						LogUtils.error("日终补漏 | " + e.getMessage());
						e.printStackTrace();
					}
				}
				KafkaUtil.close();
			} else {
				LogUtils.debugLog("日终补漏 | 获取MQ连接失败！");
			}
		}
		LogUtils.debugLog("************************* 日终补漏结束 !   *************************");
	}

}
