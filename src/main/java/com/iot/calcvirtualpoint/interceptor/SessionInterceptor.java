package com.iot.calcvirtualpoint.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

public class SessionInterceptor  extends HandlerInterceptorAdapter  {
    
    
    private Set<String> writeUrls ;
    
    /**
     * mweb请求拦截
     */
    private Boolean isConfig = false;
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof DefaultServletHttpRequestHandler ){
            return super.preHandle(request, response, handler);
        }
        
        //不需要登录验证
        for (String url : writeUrls) {
            if(request.getRequestURI().matches( url ) ){
                return true;
            }
        }
        
        //登录cookie是否可用
        boolean loginSession  = "yes".equals(request.getSession().getAttribute("loginSession"));
        //需要登录验证
        if(loginSession){
            return true;
        }
        //处理超时
        {
            String loginUrl = "/" ;
            response.sendRedirect(loginUrl);
        }
        return false;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
    
    public Set<String> getWriteUrls() {
        return writeUrls;
    }

    public void setWriteUrls(Set<String> writeUrls) {
        this.writeUrls = writeUrls;
    }
    public Boolean isConfig() {
        return isConfig;
    }
    public void setIsConfig(Boolean isConfig) {
        this.isConfig = isConfig;
    }
    
}
