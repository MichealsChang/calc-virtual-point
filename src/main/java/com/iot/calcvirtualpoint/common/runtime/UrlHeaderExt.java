package com.iot.calcvirtualpoint.common.runtime;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iot.calcvirtualpoint.common.constants.Configuration;
import com.iot.calcvirtualpoint.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.exue.framework.dto.UrlHeader;
import com.exue.framework.util.JacksonUtils;

public class UrlHeaderExt extends UrlHeader {

    private static final long serialVersionUID = -2066809206782647097L;
    /**
     * cookie 中的时长
     */
    public static int COOKIE_MAXAGE = -1;
    /**
     * cookie 中的名称
     */
    public static String COOKIE_URL_HEADER = "cookie_url_header";

    public static final String PARAMS_HIDE_HEADER = "hideHeader";

    public static final String PARAMS_BACK_MODE = "backMode";

    /**
     * 隐藏头
     */
    private Boolean hideHeader = Boolean.FALSE;
    /**
     * 返回原系统方式 0 自动返回，如做完登录后直接返回 1 用户点击返回
     */
    private int backMode = 0;

    public UrlHeaderExt() {

    }

    public UrlHeaderExt(HttpServletRequest req) {
        init(req);
        this.setHideHeader(Boolean.valueOf(req.getParameter(PARAMS_HIDE_HEADER)));
        this.setBackMode(NumberUtils.toInt(req.getParameter(PARAMS_BACK_MODE), 0));
    }

    public UrlHeaderExt(UrlHeaderExt urlHeader) {
        copy(urlHeader);
    }

    public void copy(UrlHeaderExt urlHeader) {
        if (null == urlHeader) {
            return;
        }
        super.copy(urlHeader);
        this.setHideHeader(urlHeader.getHideHeader());
        this.setBackMode(urlHeader.getBackMode());
    }

    public static UrlHeaderExt initReqHeader(String redirectUri) {
        UrlHeaderExt h = new UrlHeaderExt();
        h.setServiceId(Configuration.SERVICE_ID);
        h.setServiceType(Configuration.SERVICE_TYPE);
        h.setServiceVersion(Configuration.SERVICE_VERSION);
        h.setRedirectUri(redirectUri);
        return h;
    }

    /**
     * 
     * @Description: serviceId=##&...&
     * @return
     * @return String
     * @throws null
     */
    public String toReqUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(UrlHeaderExt.PARAMS_SERVICE_ID).append("=").append(this.getServiceId()).append("&");
        sb.append(UrlHeaderExt.PARAMS_SERVICE_TYPE).append("=").append(this.getServiceType()).append("&");
        sb.append(UrlHeaderExt.PARAMS_SERVICE_VERSION).append("=").append(this.getServiceVersion()).append("&");
        if (StringUtils.isNotBlank(this.getState())) {
            sb.append(UrlHeaderExt.PARAMS_STATE).append("=").append(encode(this.getState())).append("&");
        }
        if (StringUtils.isNotBlank(this.getVersion())) {
            sb.append(UrlHeaderExt.PARAMS_VERSION).append("=").append(this.getVersion()).append("&");
        }
        if (Boolean.TRUE.equals(this.getHideHeader())) {
            sb.append(UrlHeaderExt.PARAMS_HIDE_HEADER).append("=").append(this.getHideHeader()).append("&");
        }
        if (0 != this.getBackMode()) {
            sb.append(UrlHeaderExt.PARAMS_BACK_MODE).append("=").append(this.getBackMode()).append("&");
        }
        if (StringUtils.isNotBlank(this.getRedirectUri())) {
            sb.append(UrlHeaderExt.PARAMS_REDIRECTURI).append("=").append(decode(this.getRedirectUri())).append("&");
        }
        return sb.toString();
    }

    private String encode(String v) {
        if (StringUtils.isBlank(v)) {
            return v;
        }
        try {
            return URLEncoder.encode(v, Configuration.CHARSET.name());
        } catch (Exception e) {
            LogUtils.error("UnsupportedEncodingException UrlHeaderExt.encode:" + v, e);
            return v;
        }
    }

    private String decode(String v) {
        if (StringUtils.isBlank(v)) {
            return v;
        }
        try {
            return URLDecoder.decode(v, Configuration.CHARSET.name());
        } catch (Exception e) {
            LogUtils.error("UnsupportedEncodingException UrlHeaderExt.decode:" + v, e);
            return v;
        }
    }

    /**
     * 
     * @Description:根据redirectUri生成返回redirectUri?state=##
     * @return
     * @return String
     * @throws null
     */
    public String toReturnUrl() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(this.getRedirectUri())) {
            sb.append(decode(this.getRedirectUri()));
        }
        if (sb.length() > 0) {
            if (StringUtils.isNotBlank(this.getState()))
                sb.append(sb.indexOf("?") > 0 ? "&" : "?").append("state=").append(this.getState());
        }
        return sb.toString();
    }

    /**
     * 
     * @Description:是否有serviceid
     * @return boolean
     * @throws null
     */
    public boolean hasServiceId() {
        return null != this.getServiceId() && !this.getServiceId().isEmpty();
    }

    /**
     * 默认进行 URLDecoder.decode
     */
    @Override
    public void setRedirectUri(String redirectUri) {
        if (StringUtils.isEmpty(redirectUri)) {
            super.setRedirectUri(redirectUri);
            return;
        }
        // 不为空时
        super.setRedirectUri(decode(redirectUri));
    }

    /**
     * json cookie
     */
    public static void addToCookie(UrlHeaderExt req, HttpServletResponse response) {
        Cookie cookie = new Cookie(UrlHeaderExt.COOKIE_URL_HEADER, JacksonUtils.toJson(req));
        cookie.setMaxAge(UrlHeaderExt.COOKIE_MAXAGE);
        cookie.setPath("/");
        cookie.setDomain(Configuration.SSO_DOMAIN);
        response.addCookie(cookie);
    }

    /**
     * json cookie
     */
    public void addToCookie(HttpServletResponse response) {
        addToCookie(this, response);
    }

    public static void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(UrlHeaderExt.COOKIE_URL_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(Configuration.SSO_DOMAIN);
        response.addCookie(cookie);
    }

    public static UrlHeaderExt getFromCookie(HttpServletRequest request) {
        Cookie[] cs = request.getCookies();
        if (null != cs && cs.length > 0) {
            for (int i = 0; i < cs.length; i++) {
                Cookie cookie = cs[i];
                if (UrlHeaderExt.COOKIE_URL_HEADER.equals(cookie.getName())) {
                    String urlHeader = cookie.getValue();
                    if (StringUtils.isNotBlank(urlHeader)) {
                        return JacksonUtils.readValue(urlHeader, UrlHeaderExt.class);
                    }
                    break;
                }
            }
        }
        return null;
    }

    public Boolean getHideHeader() {
        return hideHeader;
    }

    public void setHideHeader(Boolean hideHeader) {
        this.hideHeader = hideHeader;
    }

    public int getBackMode() {
        return backMode;
    }

    public void setBackMode(int backMode) {
        this.backMode = backMode;
    }

    @Override
    public String toJson() {
        return JacksonUtils.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }

}
