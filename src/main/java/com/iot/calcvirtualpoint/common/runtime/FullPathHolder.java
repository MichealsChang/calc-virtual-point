package com.iot.calcvirtualpoint.common.runtime;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exue.framework.dto.Device;


public class FullPathHolder {

    private static ThreadLocal<ConcurrentHashMap<String, Object>> threadLocal = new ThreadLocal<ConcurrentHashMap<String, Object>>() {
        @Override
        protected ConcurrentHashMap<String, Object> initialValue() {
            // 线程独享对象使用ConcurentHashMap似乎没有必要
            return new ConcurrentHashMap<String, Object>();
        }
    };

    public static String toThreadLocalString() {
        return threadLocal.get().toString();
    }

    public static final String HTTP_SERVLET_REQUEST = "HttpServletRequest";
    public static final String HTTP_SERVLET_RESPONSE = "HttpServletResponse";
    public static final String REQUEST_URLHEADER = "urlHeader";

    public static final String REQUEST_SERVICEID_NAME = "serviceId";
    public static final String REQUEST_SERVICE_VERSION_NAME = "serviceVersion";
    public static final String REQUEST_SERVICE_TYPE_NAME = "serviceType";

    public static final String REQUEST_DEVICE = "device";
    public static final String REQUEST_HEADER = "header";

    /** 便于统计请求时间 */
    public static final String REQUEST_TIME = "requestTime";
    public static final String HTTP_REQUEST = "httpRequest";

    public static void setHttpServletRequest(HttpServletRequest request){
        put(HTTP_SERVLET_REQUEST, request);
    }
    
    public static HttpServletRequest getHttpServletRequest(){
        return getObj(HTTP_SERVLET_REQUEST);
    }
    
    public static void setHttpServletResponse(HttpServletResponse response){
        put(HTTP_SERVLET_RESPONSE, response);   
    }
    
    public static HttpServletResponse getHttpServletResponse(){
        return getObj(HTTP_SERVLET_RESPONSE);
    }
    
    
    public static HeaderExt getHeader() {
        return getObj(REQUEST_HEADER);
    }
    
    public static void setHeader(HeaderExt header) {
        put(REQUEST_HEADER, header);
    }
    
    public static UrlHeaderExt getUrlHeader() {
        return getObj(REQUEST_URLHEADER);
    }

    public static void setUrlHeader(UrlHeaderExt urlHeader) {
        put(REQUEST_URLHEADER, urlHeader);
    }
    public static String getServiceId() {
        return get(REQUEST_SERVICEID_NAME);
    }

    public static void setServiceId(String serviceId) {
        put(REQUEST_SERVICEID_NAME, serviceId);
    }

    public static String getServiceVersion() {
        return get(REQUEST_SERVICE_VERSION_NAME);
    }

    public static void setServiceVersion(String serviceVersion) {
        put(REQUEST_SERVICE_VERSION_NAME, serviceVersion);
    }

    public static String getServiceType() {
        return get(REQUEST_SERVICE_TYPE_NAME);

    }

    public static void setServiceType(String serviceType) {
        put(REQUEST_SERVICE_TYPE_NAME, serviceType);
    }

    public static String getRequestTime() {
        return get(REQUEST_TIME);

    }

    public static void setRequestTime(String requestTime) {
        put(REQUEST_TIME, requestTime);
    }

    public static Device getDevice() {
        return getObj(REQUEST_DEVICE);
    }

    public static void setDevice(Device device) {
        put(REQUEST_DEVICE, device);
    }

    public static Boolean isHttpRequest() {
        return getObj(HTTP_REQUEST);
    }

    public static void setHttpRequest(Boolean httpRequest) {
        put(HTTP_REQUEST, httpRequest);
    }

    public static void cleanLocal() {
        threadLocal.remove();
    }

    private static void put(String key, Object value) {
        if (key != null && value != null) {
            threadLocal.get().put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObj(String key) {
        if (key == null)
            return null;
        Object obj = threadLocal.get().get(key);
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }

    public static String get(String key) {
        if (key == null)
            return null;
        Object obj = threadLocal.get().get(key);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return null;
    }
}
