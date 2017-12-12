package com.iot.common.opentsdb.tsdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TSRestClient {
	private Logger logger = LoggerFactory.getLogger(TSRestClient.class);
	private List<String> hosts = new ArrayList<String>();
	private List<RestClient> clients = new ArrayList<RestClient>();
	public void addHost(String host, int port) {
		this.hosts.add(host+":"+port);
	}

	public String getBaseUrl() {
		Random r = new Random();
		int i = r.nextInt(hosts.size());
		return "http://" + hosts.get(i);	
	}
	public RestClient getRestClient() {
		Random r = new Random();
		int i = r.nextInt(clients.size());
		return this.clients.get(i);
	}
	public void setRestClient(RestClient client) {
		this.clients.add(client);
	}
	public TSResult post(String dataString, String url) {
		TSResult result = new TSResult();
		ClientResponse response = null;
		
		try {
			Resource resource = getRestClient().resource(this.getBaseUrl() + url);
			resource.contentType("application/json");
			response = resource.post(dataString);
			
			int status = response.getStatusCode();
			if (status == HttpStatus.SC_OK) {		
				String entity = response.getEntity(String.class);
				result = this.parseResult(JSON.parseArray(entity, String.class));
			} else if (status == HttpStatus.SC_NO_CONTENT) {
				//content = "[]";
				//result = this.parseResult(JSON.parseArray(content, clazz));
			} else {
				response = resource.post(dataString);
				status = response.getStatusCode();
				if (status == HttpStatus.SC_OK) {		
					String entity = response.getEntity(String.class);
					result = this.parseResult(JSON.parseArray(entity, String.class));
				} else if (status == HttpStatus.SC_NO_CONTENT) {
					//content = "[]";
					//result = this.parseResult(JSON.parseArray(content, clazz));
				} else {
					logger.error(response.getStatusCode() + ":" + response.getMessage());
					TSError err = new TSError(response.getStatusCode() + ":" +response.getMessage());
					result.getErrors().add(err);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			String msg = e.getMessage();			
			TSError error = new TSError(msg);
			result.getErrors().add(error);
			return result;
		} finally {

		}
		
		return result;
	}
	private  <T> TSResult parseResult(List<T> rtn) {
		TSResult result = new TSResult();
		for (int i = 0; i < rtn.size(); i++) {
			JSONObject jsResult = (JSONObject) JSONObject.parse(rtn.get(i).toString());
			if (jsResult != null) {	
				String metric = jsResult.get("metric").toString();
				JSONObject tags = (JSONObject)jsResult.get("tags");
				TSMetricData data = new TSMetricData(
						metric,
						tags.get("key")==null?"":tags.get("key").toString()
				);
				JSONObject jsDps = (JSONObject)jsResult.get("dps");
				if (jsDps != null) {
					Iterator<?> iter = jsDps.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry)iter.next();
						long ts = Long.parseLong(entry.getKey().toString());
						double val = Double.parseDouble(entry.getValue().toString());
						TSDataPoint dp = new TSDataPoint(ts, val);
						data.getDps().add(dp);
					}
				}
				result.getData().add(data);
			}
		}
		return result;	
	}
}
