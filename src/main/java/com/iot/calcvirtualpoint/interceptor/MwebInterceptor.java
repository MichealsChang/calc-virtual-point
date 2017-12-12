package com.iot.calcvirtualpoint.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iot.calcvirtualpoint.common.runtime.FullPathHolder;
import com.iot.calcvirtualpoint.common.runtime.UrlHeaderExt;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.exue.framework.util.IpUtils;

public class MwebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            if (StringUtils.isNotBlank(FullPathHolder.getUrlHeader().getServiceType())) {
                StringBuilder sb = new StringBuilder(64);
                sb.append("[mweb req]");
                sb.append("[serviceId]");
                sb.append(FullPathHolder.getUrlHeader().getServiceId());
                sb.append("[serviceType]");
                sb.append(FullPathHolder.getUrlHeader().getServiceType());
                sb.append("[ip]");
                sb.append(IpUtils.getRemoteIpAddr(request));
                sb.append("[uri]");
                sb.append(request.getRequestURI());
                sb.append("[param]");
                sb.append(request.getQueryString());
                LogUtils.serviceLog(sb.toString());
            }

        } catch (Throwable e) {
            LogUtils.error("mweb req log error", e);
        }

        UrlHeaderExt uhe = FullPathHolder.getUrlHeader();

        // 是否隐藏header
        if (Boolean.TRUE.equals(uhe.getHideHeader())) {
            request.setAttribute(UrlHeaderExt.PARAMS_HIDE_HEADER, true);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
