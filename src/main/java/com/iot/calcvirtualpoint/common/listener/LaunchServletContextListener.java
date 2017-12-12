package com.iot.calcvirtualpoint.common.listener;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.constants.ConstantParams;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.exue.framework.entity.Entry;

/**
 * 项目启动时预先设置的参数
 * @author daizq
 *
 */
public class LaunchServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    	LogUtils.debugLog("Servlet destroy!!!");
    }

    
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	System.out.println("Servlet Initialized!!!");
        ServletContext sc = event.getServletContext();
        
        Entry<String, Integer> entry = null;
        int port = 0;
        String ip = null;
        
//        ip = IpUtils.getLocalIpAddr();
        ip = "127.0.0.1";
        String projectPort = sc.getInitParameter(ConstantParams.SYSTEM_PROJECT_PORT);
        if(NumberUtils.isNumber(projectPort) && Integer.parseInt(projectPort) > 0){
            port = Integer.parseInt(projectPort);
        }else{
            entry = getPort(sc);
            port = entry.getMsg();
        }
        
        System.setProperty(ConstantParams.SYSTEM_PROJECT_PORT,String.valueOf(port));
        System.setProperty(ConstantParams.SYSTEM_PROJECT_IP,ip);
        System.setProperty(ConstantParams.SYSTEM_LOGGING_ROOT, Configuration.SYSTEM_LOGGING_ROOT);
        
        if(null != entry && !entry.isSuccess()){
            LogUtils.error("project.port init failure");
        }
    }

    private Entry<String, Integer> getPort(ServletContext sc){
        Entry<String, Integer> entry = new Entry<String, Integer>();
        
        String realPath = sc.getRealPath("/");
        
        String regex = sc.getInitParameter(ConstantParams.SYSTEM_PATTERN_PORT);
        if(StringUtils.isNotBlank(regex)){
            Matcher matcher = Pattern.compile(regex).matcher(realPath);
            if(matcher.matches()){
                return entry.put(Entry.SUCCESS, Integer.parseInt(matcher.group(1))) ;
            }
        }
        
        return entry.put(Entry.ERROR, 10000+new Random().nextInt(10000)) ;
    }
    
    
}
