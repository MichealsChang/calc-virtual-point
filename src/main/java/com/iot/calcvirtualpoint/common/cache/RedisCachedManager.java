package com.iot.calcvirtualpoint.common.cache;

import java.io.Serializable;
import java.util.Map;

public interface RedisCachedManager {

	/**
	 * 添加操作(对象)
	 * 
	 * @param key
	 * @param o
	 * @return
	 */
	public boolean set(String key, String o);
	/**
	 * 添加操作(对象)
	 * 
	 * @param key
	 * @param o
	 * @return
	 */
	public boolean set(String key, Serializable o);
	
	/**
	 * 对应于set操作，没有就add，有就replace
	 * 
	 * @param key
	 * @param exprieInSecond
	 *            超时时间单位秒
	 * @param o
	 * @return
	 */
	public boolean set(String key, int exprieInSecond, String o);
	/**
	 * 对应于set操作，没有就add，有就replace
	 * 
	 * @param key
	 * @param exprieInSecond
	 *            超时时间单位秒
	 * @param o
	 * @return
	 */
	public boolean set(String key, int exprieInSecond, Serializable o);
    /**
     * 取单个信息(对象)
     * 
     * @param key
     * @return Object
     */
    public String get(String key);
    /**
     * 取单个信息(对象)
     * 
     * @param key
     * @return Object
     */
    public <T extends Serializable> T get(String key, Class<T> clazz);

    /**
     * 取多个信息
     * 
     * @param key
     * @return
     */
    public Map<String, String> getMultiString(String... key);

    /**
     * 删除 本类总接口全部捕获异常
     * 
     * @param key
     * @return
     */
    public boolean remove(String key);

    /**
     * 将相关key的value加一返回
     */
    public long addOrIncr(String key);

    /**
     * 将相关key的value加指定值返回现
     */
    public long addOrIncr(String key, int incrValue);
    
    /**
	 * 
	 * @Description:设置过期时间
	 * @param key
	 * @param exprieInSecond
	 *            单位：秒
	 * @return boolean
	 * @throws
	 */
	boolean expire(String key, int exprieInSecond);

	/**
	 * 
	 * @Description:判断key是否存在
	 * @param key
	 * @return boolean
	 * @throws
	 */
	boolean hasKey(String key);
	
	/**
	 * 
	 * @Description:获取hash
	 * @param key
	 * @return Map<String,String>
	 * @throws
	 */
	Map<String, String> getHash(String key);
	/**
	 * 
	 * @Description:
	 * @param key
	 * @param hashKey
	 * @return Object
	 * @throws
	 */
	String getHash(String key, String hashKey);
	/**
	 * 
	 * @Description:
	 * @param key
	 * @param m
	 * @return boolean
	 * @throws
	 */
	boolean setHash(String key, Map<String, String> m) ;
	
	/**
	 * 
	 * @Description:
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 * @return boolean
	 * @throws
	 */
	boolean setHash(String key, String hashKey, String hashValue) ;
	
	/**
	 * 
	 * @Description:
	 * @param key
	 * @param hashKey
	 * @return boolean
	 */
	boolean removeHash(String key, String hashKey);
}
