package com.iot.calcvirtualpoint.run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TreeMap;

import com.iot.calcvirtualpoint.business.inf.ITaskExeStatusService;
import com.iot.calcvirtualpoint.business.inf.ITaskIplDetailService;
import com.iot.calcvirtualpoint.business.inf.ITaskIplFailedService;
import com.iot.calcvirtualpoint.business.inf.ITaskIplLogService;
import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.runtime.ApplicationContextHolder;
import com.iot.calcvirtualpoint.common.util.DateTimeUtil;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import com.iot.calcvirtualpoint.model.*;
import com.iot.calcvirtualpoint.util.FormulaCalculator;
import com.iot.calcvirtualpoint.util.KafkaUtil;
import com.iot.common.opentsdb.tsdb.*;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.exue.framework.entity.BaseList;
import com.exue.framework.entity.BaseResp;
import com.ibm.icu.text.SimpleDateFormat;

public class IplTask extends BaseTask {

	private ITaskIplLogService taskIplLogService = ApplicationContextHolder.getBean("taskIplLogServiceImpl");
	private ITaskIplDetailService taskIplDetailService = ApplicationContextHolder.getBean("taskIplDetailServiceImpl");
	private ITaskIplFailedService taskIplFailedService = ApplicationContextHolder.getBean("taskIplFailedServiceImpl");
	private ITaskExeStatusService taskExeStatusService = ApplicationContextHolder.getBean("taskExeStatusServiceImpl");

	public IplTask() {

	}

	public IplTask(String taskId) {
		setTaskId(taskId);
	}

	private Map<TaskComMapper, List<TaskCalculate>> taskComMap = new HashMap<TaskComMapper, List<TaskCalculate>>();
	private ConcurrentHashMap<String, Integer> isQuery = new ConcurrentHashMap<String, Integer>();
	TaskIplLog log = null;
	// 获取tsdb连接
	TSClient client = new TSClient(Configuration.TSDB_IP, Configuration.TSDB_PORT);

	private int successCount = 0;
	private int failedCount = 0;

	@SuppressWarnings("deprecation")
	@Override
	public void handle() {
		LogUtils.debugLog("taskId=" + getTaskId() + " | ============================================虚点清算start==============================================");
		LogUtils.debugLog("taskId=" + getTaskId() + " | 任务执行：" + this.getTaskId() + ",now:" + new Date());
		LogUtils.debugLog("taskId=" + getTaskId() + " | taskComMap.size：" + taskComMap.size());
		long mills = System.currentTimeMillis();
		long exectime = 0;
		String finishTime = null;
		Date date = new Date(mills);

		// 先查询taskId的任务执行状态存不存在，如果存在，则更新，
		TaskExeStatus t = new TaskExeStatus();
		t.setTaskId(getTaskId());
		BaseList<TaskExeStatus> baseList = taskExeStatusService.findList(t);
		if (baseList.getTotalRows() > 0) {
			t = baseList.getList().get(0);
			t.setStatus("running");
			taskExeStatusService.update(t);
			LogUtils.debugLog("taskId=" + getTaskId() + " | 更新线程执行状态...");
		} else {
			t.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
			t.setStatus("running");
			taskExeStatusService.insert(t);
			LogUtils.debugLog("taskId=" + getTaskId() + " | 创建线程执行状态...");
		}

		// 循环task下的企业
		Iterator<Entry<TaskComMapper, List<TaskCalculate>>> iterator = taskComMap.entrySet().iterator();
		// 获取MQ连接
		boolean conn = KafkaUtil.open(Configuration.KAFKA_BROKERS, Configuration.KAFKA_TOPIC);
		LogUtils.debugLog("taskId=" + getTaskId() + " | 获取MQ连接是否成功：" + conn);
		if (conn) {
			while (iterator.hasNext()) {
				try {

					successCount = 0;
					failedCount = 0;

					Entry<TaskComMapper, List<TaskCalculate>> entry = iterator.next();
					TaskComMapper taskComMapper = entry.getKey();
					int timedif = taskComMapper.getTimeDif();

					String endTime = DateTimeUtil.format(DateTimeUtil.addMinutes(date, -timedif), "yyyy/MM/dd HH:mm:ss");
					Date datedif = DateTimeUtil.parseDatetime(endTime, "yyyy/MM/dd HH:mm:ss");
					String startTime = DateTimeUtil.format(DateTimeUtil.addMinutes(datedif, -15), "yyyy/MM/dd HH:mm:ss");

					String tm = DateTimeUtil.getLast15MinutesDateByDate(endTime);
					// 默认值
					long timestamp = DateTimeUtil.parseDatetime(tm, "yyyy/MM/dd HH:mm:ss").getTime();

					log = new TaskIplLog();
					log.setTaskId(getTaskId());
					log.setConsId(taskComMapper.getConsId());
					log.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
					log.setBegTime(startTime);
					log.setEndTime(endTime);
					log.setStatus("执行中");
					BaseResp resp = taskIplLogService.insert(log);
					LogUtils.debugLog("taskId=" + getTaskId() + " | createTaskIplLog | " + resp.isSuccess());
					BaseList<TaskIplLog> tilList = taskIplLogService.findList(log);
					if (tilList.getTotalRows() > 0) {
						BeanUtils.copyProperties(tilList.getList().get(0), log);
						LogUtils.debugLog("taskId=" + getTaskId() + " | 获取日志主表数据成功！ | " + log.toMap());
					}

					// 循环对应企业下的虚点计算公式
					Map<String, Integer> failMsidMap = new HashMap<String, Integer>();
					List<TaskCalculate> taskCalcList = entry.getValue();
					int level = -1;
					for (TaskCalculate tc : taskCalcList) {
						if (level == -1) {
							level = tc.getCalcLevel();
						} else {
							if (level != tc.getCalcLevel()) {
								LogUtils.debugLog("taskId=" + getTaskId() + " | 将要计算下一个计算级别，程序休眠 " + (Configuration.CALC_WAIT_TIME / 1000) + " s...");
								Thread.sleep(Configuration.CALC_WAIT_TIME);
								LogUtils.debugLog("taskId=" + getTaskId() + " | **************************************************************");
								level = tc.getCalcLevel();
							}
						}
						String key = tc.getConsId() + "_" + tc.getMsId() + "_" + tc.getDataItemCode() + "_" + tm;
						if (isQuery.containsKey(key)) {
							continue;
						}

						String formula = tc.getCalcFormula();
						LogUtils.debugLog("taskId=" + getTaskId() + " | 计算公式：" + formula);
						Map<Integer, Object> params = new TreeMap<Integer, Object>();
						Map<Integer, String> values = new TreeMap<Integer, String>();
						// 循环获取都有哪些参数
						for (int i = 1; i < 53; i++) {
							// 通过get方法的反射，获取参数的值
							Object param = getValueByGetMethodName(tc, i);
							if (param != null && !"".trim().equals(param)) {
								// 有值的参数加入map集合，以便以后的数据获取...
								params.put(i, param);
								if (tc.getCalcLevel() != 0) {
									if (failMsidMap.containsKey(tc.getConsId() + "_" + param + "_" + tc.getDataItemCode() + "_" + tm)) {
										failMsidMap.put(key, null);
										LogUtils.syndataerrorLog("taskId=" + getTaskId() + " | " + tc.getCalcLevel() + "：" + key);

										TaskIplFailed failed = new TaskIplFailed();
										failed.setCalcId(tc.getId());
										failed.setBegTime(startTime);
										failed.setEndTime(endTime);
										BaseList<TaskIplFailed> findList = taskIplFailedService.findList(failed);
										if (findList.getTotalRows() == 0) {
											// 查询失败记录写入
											failed.setCalcId(tc.getId());
											failed.setBegTime(startTime);
											failed.setEndTime(endTime);
											failed.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
											failed.setRemarks("子级监测点查询无数据返回");
											taskIplFailedService.insert(failed);
											LogUtils.debugLog("taskId=" + getTaskId() + " | 写入失败的记录数据到失败记录数据库表...");
										}
									}
								}
							}
							if (param == null) {
								break;
							}
						}
						LogUtils.debugLog("taskId=" + getTaskId() + " | 参数个数：" + params.size());

						// 查询实点数据
						for (Entry<Integer, Object> en : params.entrySet()) {
							TSQuery q = new TSQuery();
							q.setAggregator("none");
							q.setKey(en.getValue().toString());
							q.setMetric(tc.getDataItemCode());
							q.setStart(startTime);
							q.setEnd(endTime);
							long start = System.currentTimeMillis();
							TSResult r = client.readAllData(q);
							long end = System.currentTimeMillis();
							LogUtils.debugLog("taskId=" + getTaskId() + " | query|" + JSONObject.toJSONString(q) + "|耗时：" + (end - start) + " ms...");
							if (r != null) {
								if (r.hasError()) {
									LogUtils.debugLog("taskId=" + getTaskId() + " | read from " + r.getHost() + ":" + r.getPort() + " failure!");
									for (TSError error : r.getErrors()) {
										LogUtils.debugLog("taskId=" + getTaskId() + " | " + error.getMessage());
									}

									TaskIplFailed failed = new TaskIplFailed();
									failed.setCalcId(tc.getId());
									failed.setBegTime(startTime);
									failed.setEndTime(endTime);
									BaseList<TaskIplFailed> findList = taskIplFailedService.findList(failed);
									if (findList.getTotalRows() == 0) {
										// 查询失败记录写入
										failed.setCalcId(tc.getId());
										failed.setBegTime(startTime);
										failed.setEndTime(endTime);
										failed.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
										failed.setRemarks("读取监测点数据返回报错");
										taskIplFailedService.insert(failed);
										LogUtils.debugLog("taskId=" + getTaskId() + " | 写入失败的记录数据到失败记录数据库表...");
									}
								} else {
									LogUtils.debugLog("taskId=" + getTaskId() + " | read from " + r.getHost() + ":" + r.getPort() + " success!");
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
													LogUtils.debugLog("taskId=" + getTaskId() + " | " + "tm:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dp.getTimestamp()) + ", " + "value:" + dp.getValue());
												}
											}
										} else {
											values.put(en.getKey(), "0");
											failMsidMap.put(key, null);
											LogUtils.syndataerrorLog("taskId=" + getTaskId() + " | " + tc.getCalcLevel() + "：" + key);
											TaskIplFailed failed = new TaskIplFailed();
											failed.setCalcId(tc.getId());
											failed.setBegTime(startTime);
											failed.setEndTime(endTime);
											BaseList<TaskIplFailed> findList = taskIplFailedService.findList(failed);
											if (findList.getTotalRows() == 0) {
												// 查询失败记录写入
												failed.setCalcId(tc.getId());
												failed.setBegTime(startTime);
												failed.setEndTime(endTime);
												failed.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
												failed.setRemarks("读取监测点数据无数据返回");
												taskIplFailedService.insert(failed);
												LogUtils.debugLog("taskId=" + getTaskId() + " | 写入失败的记录数据到失败记录数据库表...");
											}
										}
									}
								}
							}
						}
						LogUtils.debugLog("taskId=" + getTaskId() + " | 从TSDB中获取值的个数：" + values.size());

						// 根据公式进行计算
						for (Entry<Integer, String> entryV : values.entrySet()) {
							formula = formula.replaceFirst("P" + entryV.getKey(), entryV.getValue());
						}
						LogUtils.debugLog("taskId=" + getTaskId() + " | 参数处理过后的计算公式：" + formula);

						double realValue = FormulaCalculator.getResult(formula);
						LogUtils.debugLog("taskId=" + getTaskId() + " | 最终计算结果值：" + realValue);

						// 写入MQ
						DataPoint data = new DataPoint();
						data.setKey(tc.getMsId());
						data.setTimestamp(timestamp);
						data.setValues(new String[]{tc.getDataItemCode()+"="+realValue});

						String msg = JSONObject.toJSONString(data).toUpperCase();
						LogUtils.debugLog("taskId=" + getTaskId() + " | 将要写入MQ中的消息：" + msg);
						/*boolean success = MQClient.send(msg);
						if (success) {
							LogUtils.debugLog("taskId=" + getTaskId() + " | 写入MQ成功...");
							successCount++;
							isQuery.put(key, 1);
							// 详细日志的写入
							TaskIplDetail detail = new TaskIplDetail();
							detail.setMsId(tc.getMsId());
							detail.setLogId(log.getId());
							detail.setTime(DateTimeUtil.format(DateTimeUtil.parse(tm, "yyyy/MM/dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
							detail.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
							detail.setTableName(getLogDetailTableName());
							taskIplDetailService.insert(detail);
							LogUtils.debugLog("taskId=" + getTaskId() + " | 写入日志明细到数据库表...");
						} else {
							LogUtils.debugLog("taskId=" + getTaskId() + " | 写入MQ失败...");
							failedCount++;
							// 失败记录写入
							TaskIplFailed failed = new TaskIplFailed();
							failed.setCalcId(tc.getId());
							failed.setBegTime(startTime);
							failed.setEndTime(endTime);
							BaseList<TaskIplFailed> findList = taskIplFailedService.findList(failed);
							if (findList.getTotalRows() == 0) {
								// 查询失败记录写入
								failed.setCalcId(tc.getId());
								failed.setBegTime(startTime);
								failed.setEndTime(endTime);
								failed.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
								failed.setRemarks("写入MQ失败");
								taskIplFailedService.insert(failed);
								LogUtils.debugLog("taskId=" + getTaskId() + " | 写入失败的记录数据到失败记录数据库表...");
							}
						}*/

						KafkaUtil.sendMessage(msg);
						LogUtils.debugLog("taskId=" + getTaskId() + " | 写入MQ成功...");
						successCount++;
						isQuery.put(key, 1);
						// 详细日志的写入
						TaskIplDetail detail = new TaskIplDetail();
						detail.setMsId(tc.getMsId());
						detail.setLogId(log.getId());
						detail.setTime(DateTimeUtil.format(DateTimeUtil.parse(tm, "yyyy/MM/dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
						detail.setCreateTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
						detail.setTableName(getLogDetailTableName());
						taskIplDetailService.insert(detail);
						LogUtils.debugLog("taskId=" + getTaskId() + " | 写入日志明细到数据库表...");

						LogUtils.debugLog("taskId=" + getTaskId() + " | +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					}
					long end = System.currentTimeMillis();
					finishTime = DateTimeUtil.formatDatetime(new Date());
					// TODO 由于需求单位是分钟，由于时间太小，转换分钟的话是0，所以现在暂时调成秒，以后再改，
					exectime = (end - mills) / 1000;
				} catch (Exception e) {
					LogUtils.error("taskId=" + getTaskId() + " | " + e.getMessage());
					e.printStackTrace();
				} finally {
					if (successCount == 0 && failedCount == 0 && log != null) {
						taskIplLogService.deleteById(log.getId());
						LogUtils.debugLog("taskId=" + getTaskId() + " | 删除多余日志主表！");
					} else {
						log.setFinishTime(finishTime);
						log.setExeConsuming((int) exectime);
						log.setExeResult(failedCount == 0 ? "成功" : "失败");
						log.setSucessNum(successCount);
						log.setFailNum(failedCount);
						log.setStatus("完成");
						// 更新日志表状态等
						taskIplLogService.update(log);
						LogUtils.debugLog("taskId=" + getTaskId() + " | 更新日志主表状态！，成功：" + successCount + " | 失败：" + failedCount);
					}
				}
			}
			baseList = taskExeStatusService.findList(t);
			if (baseList.getTotalRows() > 0) {
				t = baseList.getList().get(0);
				t.setStatus("success");
				t.setLastExeTime(DateTimeUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
				t.setLastTimeConsuming((int) (System.currentTimeMillis() - mills) / 1000);
				taskExeStatusService.update(t);
				LogUtils.debugLog("taskId=" + getTaskId() + " | 更新线程状态表！");
			}
			KafkaUtil.close();
			LogUtils.debugLog("taskId=" + getTaskId() + " | ============================================虚点清算end============================================");
		} else {
			LogUtils.debugLog("taskId=" + getTaskId() + " | 获取MQ连接失败！");
		}
	}

	public Object getValueByGetMethodName(Object obj, int i) {
		Method method = null;
		Object value = null;
		String getter = "getP" + i;
		try {
			method = obj.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(obj, new Object[] {});
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 根据taskid的尾号来获取对应的logdetail表名
	 * @return
	 */
	private String getLogDetailTableName() {
		String taskId = this.getTaskId();
		if (taskId.length() >= 2) {
			return Configuration.LJTH_DATASYN_LOG_DETAIL_TABLE_NAME + taskId.substring(taskId.length() - 1, taskId.length());
		} else {
			return Configuration.LJTH_DATASYN_LOG_DETAIL_TABLE_NAME + taskId;
		}
	}

	public void setIsQuery(ConcurrentHashMap<String, Integer> isQuery) {
		this.isQuery = isQuery;
	}

	public ConcurrentHashMap<String, Integer> getIsQuery() {
		return isQuery;
	}

	public Map<TaskComMapper, List<TaskCalculate>> getTaskComMap() {
		return taskComMap;
	}

	public void setTaskComMap(Map<TaskComMapper, List<TaskCalculate>> taskComMap) {
		this.taskComMap = taskComMap;
	}

}
