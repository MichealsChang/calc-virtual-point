package com.iot.calcvirtualpoint.common.util;

import java.util.Random;

import com.exue.framework.util.DateUtils;


public class GenerateUtil {

	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	private static Random randNumber = new Random();
	private static char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	/**
	 * 生成appkey 30位长度字符串
	 * 
	 * @Title: generateAppkey
	 * @Description:
	 * @Author zguowei jakbb01@gmail.com
	 * @Time 2013-3-8 下午3:31:03
	 * @return
	 * @return String
	 * @throws
	 */
	public static final String generateAppkey() {
		String unixTime = String.valueOf(DateUtils.getUnixTime());
		String appkey = unixTime.concat(getRandomStr(20));
		return appkey;
	}

	/**
	 * 生成App通信密钥 24位字符串长度
	 * 
	 * @Title: generateAppSecret
	 * @Description:
	 * @Author zguowei jakbb01@gmail.com
	 * @Time 2013-3-8 下午3:31:53
	 * @return
	 * @return String
	 * @throws
	 */
	public static final String generateAppSecret() {
		return getRandomStr(24);
	}

	public static final String getRandomStr(int length) {

		if (length < 1) {
			return null;
		}

		char[] randBuffer = new char[length];

		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}

		return new String(randBuffer);
	}

	/**
	 * 生成validCode六位随机验证码
	 * 
	 * @Title: generateValidCode
	 * @Author:guobifeng
	 * @return String
	 * @throws
	 */
	public static final String generateValidCode() {
		return getRandomNumber(6);
	}

	public static final String getRandomNumber(int length) {

		if (length < 1) {
			return null;
		}

		char[] randBuffer = new char[length];

		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbers[randNumber.nextInt(9)];
		}

		return new String(randBuffer);
	}

	/**
	 * 密码加密时使用盐值 生成规则为5位随即字符数字
	 * 
	 * @return
	 */
	public static final String getPwdSalt() {
		return getRandomStr(5);
	}

	/**
	 * 生成6位随机密码
	 * 
	 * @return
	 */
	public static final String getPwd() {
		return getRandomStr(6);
	}

	public static void main(String[] agrs) {
		System.out.println(GenerateUtil.generateAppSecret());
	}

}
