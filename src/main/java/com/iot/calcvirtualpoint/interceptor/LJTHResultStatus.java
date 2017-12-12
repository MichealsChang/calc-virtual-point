package com.iot.calcvirtualpoint.interceptor;

import com.exue.framework.constant.RespStatus;
import com.exue.framework.entity.BaseStatus;



/**
 * @Title: LJTHResultStatus.java
 * @Package com.woxianting.exue.model.status
 * @author daizq
 * @Company www.woxianting.com
 * @date 2015年11月4日 下午5:57:05
 * @version V1.0
 */
public class LJTHResultStatus extends RespStatus {

    private static final long serialVersionUID = -7793740934230952195L;
    
    /** 101001, "系统异常" */
    public static final BaseStatus SYSTEM_EXCEPTION = new BaseStatus(101001, "系统异常");
    /** 101002, "报文内容为空" */
    public static final BaseStatus CONTENT_NULL = new BaseStatus(101002, "报文内容为空");
    /** 101003, "报文内容非xml格式" */
    public static final BaseStatus CONTENT_FORMAT_ERROR = new BaseStatus(101003, "报文内容非xml格式");
    /** 101004, "请求类型reqtype为空或者类型不支持，目前仅支持03|04|05|06|10" */
    public static final BaseStatus REQTYPE_ERROR = new BaseStatus(101004, "请求类型reqtype为空或者类型不支持，目前仅支持03|04|05|06|10");
    /** 101005, "企业ID为空或非法" */
    public static final BaseStatus COMPANYID_ERROR = new BaseStatus(101005, "企业ID为空或非法");
    /** 101006, "报文数据体错误，设备号不能包含非数字字符" */
    public static final BaseStatus SEEID_ERROR = new BaseStatus(101006, "报文数据体错误，设备号不能包含非数字字符");
    /** 101007, "报文时间格式错误" */
    public static final BaseStatus TIME_FORMAT_ERROR = new BaseStatus(101007, "报文时间格式错误");
    /** 101008, "报文数据体错误，事件编号不能包含非数字字符" */
    public static final BaseStatus EVENT_ERROR = new BaseStatus(101008, "报文数据体错误，事件编号不能包含非数字字符");
    /** 101009, "报文数据体错误，设备状态不能包含非数字字符" */
    public static final BaseStatus STATUS_ERROR = new BaseStatus(101009, "报文数据体错误，设备状态不能包含非数字字符");
    /** 101010, "报文重复" */
    public static final BaseStatus RESUBMIT_ERROR = new BaseStatus(101010, "报文重复");
    /** 101011, "报文数据体错误，数据类型错误或者不支持" */
    public static final BaseStatus DATA_TYPE_ERROR = new BaseStatus(101011, "报文数据体错误，数据类型错误或者不支持");
    /** 101012, "报文数据体错误，数据值为空或格式错误" */
    public static final BaseStatus DATA_VALUE_ERROR = new BaseStatus(101012, "报文数据体错误，数据值为空或格式错误");
    /** 101013, "报文数据体错误，英文','分隔的谐波数据值为空或格式错误" */
    public static final BaseStatus DATA_HARMONIC_VALUE_ERROR = new BaseStatus(101013, "报文数据体错误，英文','分隔的谐波数据值为空或格式错误");
    /** 101014, "报文数据体错误，最值数据值错误" */
    public static final BaseStatus DATA_MAXMIN_VALUE_ERROR = new BaseStatus(101014, "报文数据体错误，最值数据值错误");
    /** 101015, "报文数据体错误，最值时间点错误" */
    public static final BaseStatus DATA_MAXMIN_TIME_ERROR = new BaseStatus(101015, "报文数据体错误，最值时间点错误");
    /** 101016, "报文数据体错误，最值平均值错误" */
    public static final BaseStatus DATA_MAXMIN_AVG_VALUE_ERROR = new BaseStatus(101016, "报文数据体错误，最值平均值错误");
    
    
    
    
}
