package com.iot.calcvirtualpoint.common.runtime;

import javax.servlet.http.HttpServletRequest;

import com.exue.framework.dto.Header;


public class HeaderExt extends Header {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8863528244392124295L;

    public HeaderExt() {

    }

    public HeaderExt(HttpServletRequest req) {
        init(req);
    }

    public HeaderExt(HeaderExt header) {
        this.setServiceId(header.getServiceId());
        this.setServiceType(header.getServiceType());
        this.setServiceVersion(header.getServiceVersion());
        this.setState(header.getState());
        this.setVersion(header.getVersion());
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

}
