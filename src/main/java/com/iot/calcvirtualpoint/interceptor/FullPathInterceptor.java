package com.iot.calcvirtualpoint.interceptor;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iot.calcvirtualpoint.common.runtime.ApiHolder;
import com.iot.calcvirtualpoint.common.runtime.FullPathHolder;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.exue.framework.util.IpUtils;

/**
 * 
 * @Description: 总拦截器，可做以下内容<br>
 *               1、访问日志拦截器<br>
 */
public class FullPathInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /** 清理Holder */
        FullPathHolder.cleanLocal();
        ApiHolder.cleanLocal();

        Long curTime = System.currentTimeMillis();
        Random random = new Random();

        /** 同一个请求，唯一标示 */
        MDC.put("requestId", "[" + curTime + "-" + random.nextInt() + "] ");

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        // 控制器访问log
        try {

            StringBuilder sb = new StringBuilder(64);

            sb.append("[ip]");
            sb.append(IpUtils.getRemoteIpAddr(request));
            sb.append("[uri]");
            sb.append(request.getRequestURI());
            sb.append("[queryString]").append(request.getQueryString());

            LogUtils.accessLog(sb.toString());

        } catch (Throwable e) {
            LogUtils.error("access log error", e);
        }

        Long curTime = System.currentTimeMillis();
        String tmpTime = FullPathHolder.getRequestTime();

        if (StringUtils.isNotBlank(tmpTime)) {

            Long startTime = Long.parseLong(tmpTime);
            StringBuffer log = new StringBuffer();
            log.append("HTTP|").append(request.getRequestURI());
            log.append("|").append((curTime - startTime));

            // XXX 测试性能打开
            LogUtils.perfDebugLog(log.toString());
        }

        /** 清除公共缓存 */
        FullPathHolder.cleanLocal();
        ApiHolder.cleanLocal();
    }

}
