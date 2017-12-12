package com.iot.calcvirtualpoint.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iot.calcvirtualpoint.common.runtime.FullPathHolder;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.exue.framework.enums.AccessTypeEnum;
import com.exue.framework.exception.ActionException;
import com.exue.framework.exception.MwebException;
import com.exue.framework.exception.SystemException;
import com.exue.framework.exception.WebException;
import com.exue.framework.util.http.HttpUtils;

public class ExceptionHandler implements HandlerExceptionResolver {
    
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object object, Exception exception) {
        
        ActionException e = null;
        
        
        switch (exception.getClass().getSimpleName()) {
        case "ActionException":
            //处理超时
            if(LJTHResultStatus.SESSION_TIME_OUT.getCode() == ((ActionException) exception).getExceptionResp().getCode()){
                e = (ActionException)exception;
                //记录为业务返回日志 
                LogUtils.serviceLog("[SESSION_TIME_OUT]异常"+"[info]"+e.toJson());
                if(dealWriteAjax(request, response, exception)){
                    return null;
                } 
                String serviceType = FullPathHolder.getUrlHeader().getServiceType();
                if(NumberUtils.isNumber(serviceType) && AccessTypeEnum.WEB.getCode() != (Integer.parseInt(serviceType))){
                    return new ModelAndView("error/mweb");
                }
                return new ModelAndView("redirect:/");
            }
        case "ApiException":
            //api处理，所有返回都为json
            e = (ActionException)exception;
            //记录为业务返回日志 
            LogUtils.serviceApiLog("[API]异常"+"[info]"+e.toJson());
            respWriter(response, e.toJson());
            return null;
            
        case "WebException":
            //web请求返回，区别同步异步
            LogUtils.serviceLog("[WEB]异常[url]"+request.getRequestURL().toString()+"[info]"+((WebException) exception).toJson());
            if(dealWriteAjax(request, response, exception)){
                return null;
            } 
            return new ModelAndView("error/web");
            
        case "MwebException":
            //mweb请求返回，区别同步异步
            LogUtils.serviceLog("[MWEB]异常[url]"+request.getRequestURL().toString()+"[info]"+((MwebException) exception).toJson());
            if(dealWriteAjax(request, response, exception)){
                return null;
            } 
            return new ModelAndView("error/mweb");

        default:
            //其它错误记录日志
            LogUtils.error("[其它]异常[url]"+request.getRequestURL().toString()+"[info]"+exception.toString(),exception);
            e = new SystemException(exception);
            //处理 AJAX
            boolean isAjax = HttpUtils.isAjax(request);
            if(isAjax){
                respWriter(response, e.toJson());
            }else{
                request.setAttribute("exceptionResp",e.getExceptionResp());
                request.setAttribute("serviceType", FullPathHolder.getUrlHeader().getServiceType());
            }
            if(!NumberUtils.isNumber(FullPathHolder.getUrlHeader().getServiceType()) 
                    || AccessTypeEnum.WEB.getCode() == Integer.parseInt(FullPathHolder.getUrlHeader().getServiceType())){
                return new ModelAndView("error/web");
            }else{
                return new ModelAndView("error/mweb");
            }
        }
    }

    /**
     * 
     * @Description:处理ajax请求
     * @Date: 2016年6月18日 上午10:52:31
     * @author dzq
     * @param request
     * @param response
     * @param exception
     * @return
     * @return boolean
     * @throws null
     */
    private boolean dealWriteAjax(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ActionException e = (ActionException) exception;
        //此为可控制异常，记录日志
        LogUtils.serviceLog(e.toJson());
        request.setAttribute("exceptionResp", e.getExceptionResp());
        request.setAttribute("serviceType", FullPathHolder.getUrlHeader().getServiceType());
        boolean isAjax = HttpUtils.isAjax(request) || e.isAjax();
        if(isAjax){
            respWriter(response, e.toJson());
        }
        return isAjax;
    }

    /**
     * 
     * @Description:写数据流到response
     * @Date: 2016年6月18日 上午10:51:40
     * @author dzq
     * @param response
     * @param str
     * @return void
     * @throws null
     */
    private void respWriter(HttpServletResponse response, String str) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.println(str);
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            LogUtils.error(ex.getMessage());
        }
    }

}