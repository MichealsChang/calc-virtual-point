package com.iot.calcvirtualpoint.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.exue.framework.util.DateUtils;


public class AccountUtils {
    /**
     * // * 用户名的正则表达式 //
     */
    // private static Pattern loginNamePattern =
    // Pattern.compile("^[\u4E00-\u9FA5a-zA-Z]{1}[\u4E00-\u9FA5_0-9a-zA-Z]{2,28}$");

    /**
     * 邮箱的正则表达式
     */
    private static Pattern emailPattern = Pattern
            .compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");

    /**
     * 手机号的正则表达式
     */
    private static Pattern telPattern = Pattern.compile("^[1-9]\\d{10}$");

    public static enum LengthKey {
        RNAME(5, 30), REMAIL(5, 35), RTel(11, 11);
        int minL;
        int maxL;

        private LengthKey(int minl, int maxl) {
            this.maxL = maxl;
            this.minL = minl;
        }
    }

    /**
     * 
     * @Description:是否合法的email
     * @param email
     * @return
     * @return boolean
     * @throws
     */
    public static boolean isCorrectEmail(String email) {
        return StringUtils.isNotBlank(email) && emailPattern.matcher(email).matches();
    }

    /**
     * 
     * @Description: 是否合法的手机号
     * @param tel
     * @return
     * @return boolean
     * @throws
     */
    public static boolean isCorrectTel(String tel) {
        return StringUtils.isNumeric(tel) && telPattern.matcher(tel).matches();
    }

    /**
     * 
     * 判断长度是否合法
     * 
     * @param args
     */
    public static boolean isCorrectLength(LengthKey type, String name) {
        if (type == null || StringUtils.isBlank(name)) {
            return false;
        }
        // 如果是用户名，单独验证字节长度
        int len = getStrLength(name);
        return len >= type.minL && len <= type.maxL;
    }

    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * serviceId（2位）+ 来源（1位）+ yymmddhhmmss(12位) + random(3位,字母+数字)
     * 
     * @param serviceId
     * @param type
     * @return
     */
    public static String genUid(String serviceId, String type) {
        StringBuffer sb = new StringBuffer();
        sb.append(serviceId).append(type).append(DateUtils.formatDate(System.currentTimeMillis(), "yyMMddHHmmss"))
                .append(GenerateUtil.getRandomStr(3));
        return sb.toString();
    }

    /**
     * Cash 账号 10开头
     * 
     * @param no
     * @return
     */
    public static String genCashAccountNo(Long no) {
        if (null == no) {
            return null;
        }
        return "10" + StringUtils.leftPad(no.toString(), 10, "0");
    }

    public static void main(String[] args) {
        System.out.println(genUid("04", "1"));
    }
}
