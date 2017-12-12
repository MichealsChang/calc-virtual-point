package com.iot.common.opentsdb.tsdb;

import com.iot.common.opentsdb.util.IoTConst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TSMetricData implements Serializable {

    private String metric = "";
    private String key = "";

    private List<TSDataPoint> dps = new ArrayList<TSDataPoint>();

    public TSMetricData() {
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public TSMetricData(String metric, String key) {
        this.metric = metric;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<TSDataPoint> getDps() {
        return dps;
    }

    public void setDps(List<TSDataPoint> dps) {
        this.dps = dps;
    }


    public boolean isSameMetric(TSMetricData t) {
        return this.key.equals(t.getKey())
                && this.getMetric().equals(t.getMetric());
    }

    public String getMetricToString() {
        return this.metric;
    }
}
