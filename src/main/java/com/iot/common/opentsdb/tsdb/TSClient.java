package com.iot.common.opentsdb.tsdb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.iot.common.opentsdb.jdeferred.DoneCallback;
import com.iot.common.opentsdb.jdeferred.impl.DefaultDeferredManager;
import com.iot.common.opentsdb.jdeferred.multiple.MultipleResults;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.RestClient;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TSClient implements Serializable {

    private final ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
    };

    private final int OFFSET = 1;
    private TSRestClient tsdClients = new TSRestClient();

    private String host;
    private int port = 4242;
    private int read_timeout = 150 * 1000;
    private int conn_timeout = 150 * 1000;

    private static volatile TSClient instance = null;

    private TSClient() {
        read_timeout = 150000;
        conn_timeout = 150000;
        initClient();
    }

    private void initClient() {
        tsdClients.addHost(host, port);
        ClientConfig config = new ClientConfig();
        config.readTimeout(read_timeout);
        config.connectTimeout(conn_timeout);
        for (int i = 0; i < 10; i++)
            tsdClients.setRestClient(new RestClient(config));
    }

    public static TSClient getInstance() {
        if (instance == null) {
            instance = new TSClient();
        }

        return instance;
    }

    /**
     * 创建tsdb客户端构造方法
     * @param host
     * @param port
     */
    public TSClient(String host, int port) {
        this.host = host;
        this.port = port;
        initClient();
    }

    public TSResult writeData(TSMetricData data) {
        List<TSMetricData> list = new ArrayList<TSMetricData>();
        list.add(data);
        return writeData(list);
    }

    public TSResult writeData(List<TSMetricData> list) {
        TSResult result = null;

        try {
            result = doWriteData(list);
        } catch (TSTimeoutException tse) {
            try {
                result = doWriteData(list);
            } catch (Exception e) {
                result = new TSResult();
                result.getErrors().add(new TSError(e.getMessage()));
            }
        }

        return result;
    }

    private TSResult doWriteData(List<TSMetricData> list) {
        TSResult result = new TSResult();

        try {
            String url = "/api/put";
            String dataString = getDataString(list);
            result = tsdClients.post(dataString, url);
            result.setHost(url);
        } catch (Exception e) {
            String msg = e.getMessage();
            TSError error = new TSError(msg);
            result.getErrors().add(error);
        }
        return result;
    }

    /**
     * 存储到tsdb,传入jsonString格式的数据
     * @param jsonString
     * @return
     */
    public TSResult doWriteData(String jsonString) {
        TSResult result = new TSResult();

        try {
            String url = "/api/put";
            result = tsdClients.post(jsonString, url);
            result.setHost(url);
        } catch (Exception e) {
            String msg = e.getMessage();
            TSError error = new TSError(msg);
            result.getErrors().add(error);
        }
        return result;
    }

    private String getDataString(List<TSMetricData> list) {
        JSONArray jsArray = new JSONArray();
        for (TSMetricData data : list) {
            List<TSDataPoint> dps = data.getDps();

            for (TSDataPoint dp : dps) {
                JSONObject jsObj = new JSONObject();
                jsObj.put("metric", data.getMetricToString());
                jsObj.put("timestamp", dp.getTimestamp());
                jsObj.put("value", dp.getStrValue());

                JSONObject jsTag = new JSONObject();

                if (!data.getKey().equals("")) {
                    jsTag.put(TSConst.TAG_MID, data.getKey());
                }

                jsObj.put("tags", jsTag);

                jsArray.add(jsObj);
            }
        }
        return jsArray.toString();
    }

    //------------------------------------------------------------------- readAllData

    public TSResult readLastData(TSQuery query) {
        TSResult result = null;

        try {
            result = doReadLastData(query);
        } catch (TSTimeoutException tse) {
            try {
                result = doReadLastData(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 根据条件查询时间范围内的数据
     */
    public TSResult readAllData(TSQuery query) {
        TSResult result = null;

        try {
            result = doReadAllData(query);
        } catch (TSTimeoutException tse) {
            try {
                result = doReadAllData(query, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private TSResult doReadAllData(final TSQuery query, boolean bOneQuery) {
        TSResult result = new TSResult();
        String url = tsdClients.getBaseUrl();
        result.setHost(url);

        JSONObject o_query = new JSONObject();
        o_query.put("start", query.getStart());
        o_query.put("end", query.getEnd());
        o_query.put("timezone", "Asia/Shanghai");
        o_query.put("useCalendar", true);

        JSONArray qs = new JSONArray();

        JSONObject q = new JSONObject();
        q.put("aggregator", query.getAggregator());

        if (query.getDownsampler() != null && !query.getDownsampler().isEmpty()) {
            String d[] = query.getDownsampler().split("-");
            if (d.length == 3) {
                if (d[2].contains("zero") &&
                        (d[0].contains("d") || d[0].contains("n") || d[0].contains("y"))) {
                    String temp = query.getDownsampler();
                    q.put("downsample", temp.replace("zero", "none"));
                } else {
                    q.put("downsample", query.getDownsampler());
                }
            } else {
                q.put("downsample", query.getDownsampler());
            }
        }
        q.put("metric", query.getMetric());
        JSONObject tagss = new JSONObject();
        if (query.getKey() != null && !query.getKey().isEmpty()) {
            tagss.put(TSConst.TAG_MID, query.getKey());
        }
        q.put("tags", tagss);
        qs.add(q);
        o_query.put("queries", qs);
        try {

            result = tsdClients.post(o_query.toString(), "/api/query");

            System.out.println(url + "/api/query?" + o_query.toString());

            if (result.hasError()) {

            } else {
                for (TSMetricData tsd : result.getData()) {
                    fillDps(tsd.getDps(), query.getStart(), query.getEnd(), query.getDownsampler());
                    Collections.sort(tsd.getDps());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TSError error = new TSError(e.getMessage());
            result.getErrors().add(error);
        }
        return result;
    }

    private TSResult doReadAllData(final TSQuery query) {
        DefaultDeferredManager dm = new DefaultDeferredManager();

        final TSResult t_rtn = new TSResult();
        final List<TSMetricData> rtn = new ArrayList<TSMetricData>();
        // initialize p1, p2, p3
        class FinishCallback implements DoneCallback<MultipleResults> {
            @Override
            public void onDone(MultipleResults result) {
                for (int i = 0; i < result.size(); i++) {
                    List<TSMetricData> lts = (List<TSMetricData>) ((TSResult) result.get(i).getResult()).getData();
                    for (TSMetricData tsd : lts) {
                        boolean ic = false;
                        for (TSMetricData ts : rtn) {
                            if (ts.isSameMetric(tsd)) {
                                ts.getDps().addAll(tsd.getDps());
                                ic = true;
                                break;
                            }
                        }
                        if (!ic) {
                            if (tsd.getDps().size() > 0) rtn.add(tsd);
                        }
                    }
                }
                if (rtn.size() == 0) {
                    TSMetricData data = new TSMetricData(query.getMetric(), query.getKey());
                    rtn.add(data);
                }
                for (TSMetricData tsd : rtn) {
                    fillDps(tsd.getDps(), query.getStart(), query.getEnd(), query.getDownsampler());
                    Collections.sort(tsd.getDps());
                }
            }
        }

        List<String[]> ls = parseDownsample(query.getDownsampler(), query.getStart(), query.getEnd());
        Callable<?>[] cls = new Callable<?>[ls.size()];
        for (int i = 0; i < ls.size(); i++) {
            TSQuery q = new TSQuery();
            q.setKey(query.getKey());
            q.setMetric(query.getMetric());
            q.setStart(ls.get(i)[1]);
            q.setEnd(ls.get(i)[2]);
            q.setDownsampler(query.getDownsampler());
            q.setAggregator(query.getAggregator());
            cls[i] = successCallable(q);
        }

        dm.when(cls).done(new FinishCallback());
        dm.shutdown();
        while (!dm.isTerminated()) {
            try {
                dm.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        t_rtn.setData(rtn);
        return t_rtn;
    }

    protected <R> Callable<R> successCallable(final TSQuery query) {
        return new Callable<R>() {

            @Override
            public R call() throws Exception {

                TSResult result = new TSResult();
                String url = tsdClients.getBaseUrl();
                result.setHost(url);

                JSONObject o_query = new JSONObject();
                o_query.put("start", query.getStart());
                o_query.put("end", query.getEnd());
                o_query.put("timezone", "Asia/Shanghai");
                o_query.put("useCalendar", true);

                JSONArray qs = new JSONArray();

                JSONObject q = new JSONObject();
                q.put("aggregator", query.getAggregator());

                if (query.getDownsampler() != null && !query.getDownsampler().isEmpty()) {
                    String d[] = query.getDownsampler().split("-");
                    if (d.length == 3) {
                        if (d[2].contains("zero") &&
                                (d[0].contains("d") || d[0].contains("n") || d[0].contains("y"))) {
                            String temp = query.getDownsampler();
                            q.put("downsample", temp.replace("zero", "none"));
                        } else {
                            q.put("downsample", query.getDownsampler());
                        }
                    } else {
                        q.put("downsample", query.getDownsampler());
                    }
                }
                q.put("metric", query.getMetric());
                JSONObject tagss = new JSONObject();
                if (query.getKey() != null && !query.getKey().isEmpty()) {
                    tagss.put(TSConst.TAG_MID, query.getKey());
                }
                q.put("tags", tagss);
                qs.add(q);
                o_query.put("queries", qs);
                try {

                    result = tsdClients.post(o_query.toString(), "/api/query");

                    System.out.println(url + "/api/query?" + o_query.toString());

                    if (result.hasError()) {
                        System.out.println(result.getErrors().get(0).toString());
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    TSError error = new TSError(e.getMessage());
                    result.getErrors().add(error);
                }
                return (R) result;
            }
        };
    }

    private List<String[]> parseDownsample(String ds, String start, String end) {
        List<String[]> rtn = new ArrayList<String[]>();
        Calendar calendar_s = Calendar.getInstance();
        Calendar calendar_e = Calendar.getInstance();
        Calendar calendar_t = Calendar.getInstance();
        Calendar calendar_f = Calendar.getInstance();
        try {
            calendar_s.setTime(df.get().parse(start.replace("-", " ")));
            calendar_e.setTime(df.get().parse(end.replace("-", " ")));
            calendar_t.setTime(df.get().parse(start.replace("-", " ")));
            calendar_t.set(Calendar.MILLISECOND, 0);
            calendar_t.set(Calendar.HOUR_OF_DAY, 23);
            calendar_t.set(Calendar.MINUTE, 59);
            calendar_t.set(Calendar.SECOND, 59);

            calendar_f.setTime(df.get().parse(start.replace("-", " ")));
            calendar_f.set(Calendar.DAY_OF_MONTH, 1);
            calendar_f.set(Calendar.MILLISECOND, 0);
            calendar_f.set(Calendar.HOUR_OF_DAY, 0);
            calendar_f.set(Calendar.MINUTE, 0);
            calendar_f.set(Calendar.SECOND, 0);

            boolean is_month = false;
            String ds_t = ds;
            String d[] = ds.split("-");
            if (d.length == 3) {
                if (d[0].contains("y")) {
                    rtn.add(new String[]{ds, start, end});
                    return rtn;
                }
                if (d[0].contains("n")) {
                    is_month = true;
                }
            }
            while (calendar_f.getTimeInMillis() < calendar_e.getTimeInMillis()) {
                Integer days = calendar_t.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar_t.set(Calendar.DAY_OF_MONTH, days);

                if (rtn.size() == 0) {
                    if (calendar_t.getTimeInMillis() > calendar_e.getTimeInMillis()) {
                        if (is_month) {
                            int temp = calendar_e.get(Calendar.DAY_OF_MONTH) - calendar_s.get(Calendar.DAY_OF_MONTH) + 1;
                            ds_t = ds.replace(d[0], temp + "d");
                        }
                        rtn.add(new String[]{ds_t, start, end});
                    } else {
                        if (is_month) {
                            int temp = calendar_t.get(Calendar.DAY_OF_MONTH) - calendar_s.get(Calendar.DAY_OF_MONTH) + 1;
                            ds_t = ds.replace(d[0], temp + "d");
                        }
                        rtn.add(new String[]{ds_t, start, df.get().format(calendar_t.getTime())});
                    }
                } else {
                    if (calendar_t.getTimeInMillis() > calendar_e.getTimeInMillis()) {
                        if (is_month) {
                            int temp = calendar_e.get(Calendar.DAY_OF_MONTH) - calendar_f.get(Calendar.DAY_OF_MONTH) + 1;
                            ds_t = ds.replace(d[0], temp + "d");
                        }
                        rtn.add(new String[]{ds_t, df.get().format(calendar_f.getTime()), end});
                    } else {
                        if (is_month) {
                            int temp = calendar_t.get(Calendar.DAY_OF_MONTH) - calendar_f.get(Calendar.DAY_OF_MONTH) + 1;
                            ds_t = ds.replace(d[0], temp + "d");
                        }
                        rtn.add(new String[]{ds_t, df.get().format(calendar_f.getTime()), df.get().format(calendar_t.getTime())});
                    }
                }
                calendar_f.add(Calendar.MONTH, OFFSET);
                calendar_t.add(Calendar.MONTH, OFFSET);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    private void fillDps(List<TSDataPoint> dps, String start, String end, String downsample) {
        if (downsample == null) return;
        if (downsample.trim().length() == 0) return;
        String ds[] = downsample.split("-");
        if (ds.length < 3) return;

        String fill = ds[2];
        if (!fill.equals("zero")) return;
        String duration = ds[0];
        if (duration.trim().length() < 2) return;

        Calendar calendar_s = Calendar.getInstance();
        Calendar calendar_e = Calendar.getInstance();
        String unit = duration.substring(duration.length() - 1);
        int interval = Integer.valueOf(duration.substring(0, duration.length() - 1));

        try {
            calendar_s.setTime(df.get().parse(start.replace("-", " ")));
            calendar_s.set(Calendar.MILLISECOND, 0);
            calendar_s.set(Calendar.HOUR, 0);
            calendar_s.set(Calendar.MINUTE, 0);
            calendar_s.set(Calendar.SECOND, 0);
            calendar_e.setTime(df.get().parse(end.replace("-", " ")));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if (!unit.equals("d") && !unit.equals("n") && !unit.equals("y")) return;
        switch (unit) {
            case "d":
                calendar_s.set(Calendar.HOUR_OF_DAY, 0);
                while (calendar_s.getTimeInMillis() < calendar_e.getTimeInMillis()) {
                    long ts = calendar_s.getTimeInMillis();
                    if (!chkPoint(ts, dps)) {
                        dps.add(new TSDataPoint(ts, 0.0));
                    }
                    calendar_s.add(Calendar.DAY_OF_MONTH, interval);
                }
                break;
            case "n":
                for (TSDataPoint tsp : dps) {
                    Calendar temp = Calendar.getInstance();
                    temp.setTimeInMillis(tsp.getTimestamp());
                    temp.set(Calendar.DAY_OF_MONTH, 1);
                    tsp.setTimestamp(temp.getTimeInMillis());
                }
                calendar_s.set(Calendar.DAY_OF_MONTH, 1);
                calendar_s.set(Calendar.HOUR_OF_DAY, 0);
                while (calendar_s.getTimeInMillis() < calendar_e.getTimeInMillis()) {
                    long ts = calendar_s.getTimeInMillis();
                    if (!chkPoint(ts, dps)) {
                        dps.add(new TSDataPoint(ts, 0.0));
                    }
                    calendar_s.add(Calendar.MONTH, interval);
                }
                break;
            case "y":
                calendar_s.set(Calendar.DAY_OF_MONTH, 1);
                calendar_s.set(Calendar.MONTH, 0);
                while (calendar_s.getTimeInMillis() <= calendar_e.getTimeInMillis()) {
                    System.out.println(df.get().format(calendar_s.getTime()));
                    long ts = calendar_s.getTimeInMillis();
                    if (!chkPoint(ts, dps)) {
                        dps.add(new TSDataPoint(ts, 0.0));
                    }
                    calendar_s.add(Calendar.YEAR, interval);
                }
                break;
            default:
                break;
        }

    }

    private boolean chkPoint(long ts, List<TSDataPoint> dps) {
        for (TSDataPoint t : dps) {
            if (t.getTimestamp() == ts) {
                return true;
            }
        }
        return false;
    }

    private TSResult doReadLastData(TSQuery query) {
        TSResult result = new TSResult();
        String url = this.tsdClients.getBaseUrl();
        result.setHost(url);

        JSONObject o_query = new JSONObject();
        JSONArray o_queries = new JSONArray();
        JSONObject o_sub = new JSONObject();
        o_sub.put("metric", query.getMetric());

        JSONObject tags = new JSONObject();
        tags.put("key", query.getKey());
        o_sub.put("tags", tags);
        o_queries.add(o_sub);

        o_query.put("queries", o_queries);
        //构造查询条件语句
        StringBuffer sb = new StringBuffer();

        //Metric
        sb.append("&timeseries=" + query.getMetric());

        //Tags
        sb.append("%7B");    // {
        String tag = "";
        if (query.getKey() != null && !query.getKey().isEmpty()) {
            if (!tag.isEmpty()) tag += ",";
            tag += TSConst.TAG_MID + "=" + query.getKey();
        }
        sb.append(tag);
        sb.append("%7D");

        try {

        } catch (Exception e) {
            TSError error = new TSError(e.getMessage());
            result.getErrors().add(error);
        }
        return result;
    }


    public TSResult readAllMsidData(TSQuery query)
    {
        TSResult result = null;
        try
        {
            result = doReadAllMsidData(query);
        } catch (TSTimeoutException tse) {
            try {
                result = doReadAllMsidData(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private TSResult doReadAllMsidData(final TSQuery query)
    {
        DefaultDeferredManager dm = new DefaultDeferredManager();
        final TSResult t_rtn = new TSResult();
        final List<TSMetricData> rtn = new ArrayList<TSMetricData>();

        class FinishCallback implements DoneCallback<MultipleResults> {
            @Override
            public void onDone(MultipleResults result) {
                for (int i=0; i< result.size(); i++) {
                    List<TSMetricData> lts = (List<TSMetricData>) ((TSResult)result.get(i).getResult()).getData();
                    for (TSMetricData tsd : lts) {
                        boolean ic = false;
                        for (TSMetricData ts : rtn) {
                            if (ts.isSameMetric(tsd)) {
                                ts.getDps().addAll(tsd.getDps());
                                ic = true;
                                break;
                            }
                        }
                        if (!ic) {
                            if (tsd.getDps().size() >0) rtn.add(tsd);
                        }
                    }
                }
                if (rtn.size() ==0&&(query.getKey() != null)) {
                    String[] msidArray = query.getKey().split("\\|");

                    String msid;
                    for (int tsd = 0; tsd < msidArray.length; tsd++) {
                         msid = msidArray[tsd];
                        TSMetricData data = new TSMetricData( query.getMetric(),msid);
                        rtn.add(data);
                    }
                }
                for (TSMetricData tsd : rtn) {
                    fillDps(tsd.getDps(), query.getStart(), query.getEnd(), query.getDownsampler());
                    Collections.sort(tsd.getDps());
                }
            }
        }

        List ls = new ArrayList();
        String[] msidArray = query.getKey().split("\\|");
        if (query.getKey() != null) {
            for (String msid : msidArray) {
                String start = query.getStart();
                String end = query.getEnd();
                String[] array = new String[3];
                array[0] = msid;
                array[1] = start;
                array[2] = end;
                ls.add(array);
            }
        }
        Callable[] cls = new Callable[ls.size()];
        for (int i = 0; i < ls.size(); i++) {
            TSQuery q = new TSQuery();
            q.setKey(((String[])ls.get(i))[0]);
            q.setMetric(query.getMetric());
            q.setStart(((String[])ls.get(i))[1]);
            q.setEnd(((String[])ls.get(i))[2]);
            q.setDownsampler(query.getDownsampler());
            q.setAggregator(query.getAggregator());
            cls[i] = successCallable(q);
        }

        dm.when(cls).done(new FinishCallback());
        dm.shutdown();
        while (!dm.isTerminated()) {
            try {
                dm.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        t_rtn.setData(rtn);
        dm.shutdown();
        return t_rtn;
    }

}
