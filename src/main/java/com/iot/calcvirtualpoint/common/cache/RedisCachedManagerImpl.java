package com.iot.calcvirtualpoint.common.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import com.iot.calcvirtualpoint.common.util.ConsistentHash;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.exue.framework.util.JacksonUtils;

@Service
public class RedisCachedManagerImpl implements RedisCachedManager {

	private static final Logger LOGGER = Logger
			.getLogger(RedisCachedManagerImpl.class);

	protected RedisTemplate<String, Object> redisTemplate;
	/**
	 * key
	 */
	private RedisSerializer<String> keySerializer = new StringRedisSerializer();
	/**
	 * String value
	 */
	private RedisSerializer<String> valueSerializer = new StringRedisSerializer();

	/**
	 * 单独选DB 优先使用
	 */
	private Integer dbIndex = null;

	/**
	 * DB列表，一致性hash分布，优先权低于 dbIndex
	 */
	List<Integer> dbNodes = new ArrayList<Integer>();

	// 一致性hash控制类
	private ConsistentHash<Integer> consistentHash = null;

	public RedisCachedManagerImpl() {

	}

	public RedisCachedManagerImpl(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 
	 * @Description:hash列表初始化方法，若使用列表，必须作为初始化方法
	 * @Date: 2014年7月21日 上午9:31:49
	 * @author daizq@woxianting.com
	 * @return void
	 * @throws null
	 */
	@PostConstruct
	public void initDbNodes() {
		if (null != dbNodes && !dbNodes.isEmpty()) {
			consistentHash = new ConsistentHash<Integer>(dbNodes);
		}
	}

	@Override
	public long addOrIncr(String key) {
		return addOrIncr(key, -1);
	}

	@Override
	public long addOrIncr(final String key, final int incrValue) {

		try {
			
			final byte[] rawKey = rawKey(key);
			return this.redisTemplate.execute(new RedisCallback<Long>() {

				public Long doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					if (incrValue > 0) {
						connection.set(rawKey, String.valueOf(incrValue).getBytes());
					}
					return connection.incr(rawKey);
				}
			}, true);

		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	/**
	 * @Description:反序列key
	 * @Date: 2014年5月21日 下午12:11:24
	 * @param key
	 * @return String
	 * @throws
	 */
	private String deserializeHashKey(byte[] bytes) {
		return keySerializer.deserialize(bytes);
	}

	/**
	 * @Description:反序列值
	 * @param bs
	 * @return String
	 * @throws
	 */
	private String deserializeValue(byte[] bs) {
		return valueSerializer.deserialize(bs);
	}

	/**
	 * @Description:反序列hash
	 * @param entries
	 * @return Map<String,Object>
	 * @throws
	 */
	private Map<String, String> deserializeHashMap(Map<byte[], byte[]> entries) {
		if (entries == null) {
			return null;
		}
		
		Map<String, String> map = new LinkedHashMap<String, String>(entries.size());
		
		for (Entry<byte[], byte[]> entry : entries.entrySet()) {
			map.put(deserializeHashKey(entry.getKey()), deserializeValue(entry.getValue()));
		}
		
		return map;
	}

	@Override
	public boolean expire(final String key, final int exprieInSecond) {
		try {

			final byte[] rawKey = rawKey(key);

			return this.redisTemplate.execute(new RedisCallback<Boolean>() {

				public Boolean doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
//					try {
//						return connection.pExpire(rawKey, exprieInSecond);
//					} catch (Exception e) {
						// Driver may not support pExpire or we may be running
						// on Redis 2.4
						return connection.expire(rawKey, exprieInSecond);
//					}
				}
			}, true);

		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	@Override
	public String get(final String key) {
		try {
			final byte[] rawKey = rawKey(key);
			Object value = this.redisTemplate.execute(
					new RedisCallback<Object>() {

						public String doInRedis(RedisConnection connection)
								throws DataAccessException {
							connection.select(chooseDb(key));
							return deserializeValue(connection.get(rawKey));
						}
					}, true);
			return null != value ? value.toString() : null;
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	public <T extends Serializable> T get(String key, Class<T> clazz) {
		String rs = get(key);
		if (StringUtils.isBlank(rs)) {
			return null;
		}
		return JacksonUtils.readValue(rs, clazz);
	}

	@Override
	public Map<String, String> getHash(final String key) {
		
		try {
			
			final byte[] rawKey = rawKey(key);
			Map<byte[], byte[]> entries = redisTemplate.execute(
					new RedisCallback<Map<byte[], byte[]>>() {
						public Map<byte[], byte[]> doInRedis(
								RedisConnection connection) {
							connection.select(chooseDb(key));
							return connection.hGetAll(rawKey);
						}
					}, true);

			return deserializeHashMap(entries);
			
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	@Override
	public Map<String, String> getMultiString(String... key) {
		Map<String, String> result = new HashMap<String, String>();
		if (key == null || key.length == 0) {
			return result;
		}
		for (String k : key) {
			result.put(k, this.get(k));
		}
		return result;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	@Override
	public boolean hasKey(final String key) {
		try {

			final byte[] rawKey = rawKey(key);

			return this.redisTemplate.execute(new RedisCallback<Boolean>() {

				public Boolean doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					return connection.exists(rawKey);
				}
			}, true);

		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	@Override
	public boolean set(final String key, final int exprieInSecond, String o) {
		try {

			final byte[] rawKey = rawKey(key);
			final byte[] rawValue = rawValue(o);

			this.redisTemplate.execute(new RedisCallback<Object>() {

				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.select(chooseDb(key));
					if (exprieInSecond <= 0) {
						connection.set(rawKey, rawValue);
					} else {
						connection.setEx(rawKey, exprieInSecond, rawValue);
					}
					return null;
				}
			}, true);

			return true;
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	/**
	 * 对应于set操作，没有就add，有就replace
	 * 
	 * @param key
	 * @param exprieInSecond
	 *            超时时间单位秒
	 * @param o
	 * @return
	 */
	public boolean set(String key, int exprieInSecond, Serializable o) {
		String os = null;
		if (null != o) {
			os = JacksonUtils.toJson(o);
		}
		return set(key, exprieInSecond, os);
	}

	@Override
	public boolean set(String key, String o) {
		return this.set(key, 0, o);
	}

	/**
	 * 添加操作(对象)
	 * 
	 * @param key
	 * @param o
	 * @return
	 */
	public boolean set(String key, Serializable o) {
		String os = null;
		if (null != o) {
			os = JacksonUtils.toJson(o);
		}
		return set(key, os);
	}

	/**
	 * @Description:
	 * @param key
	 * @return byte[]
	 * @throws
	 */
	private byte[] rawKey(String key) {
		return keySerializer.serialize(key);
	}

	/**
	 * @Description:序列化值
	 * @param o
	 * @return byte[]
	 * @throws
	 */
	private byte[] rawValue(String o) {
		return valueSerializer.serialize(o);
	}

	@Override
	public boolean remove(final String key) {
		try {
			final byte[] rawKey = rawKey(key);

			this.redisTemplate.execute(new RedisCallback<Object>() {

				public Object doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					connection.del(rawKey);
					return null;
				}
			}, true);

			return true;
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	/**
	 * @Description:key的hash mod dbSize
	 * @param key
	 * @return int
	 * @throws
	 */
	private int chooseDb(String key) {
		return dbIndex == null ? consistentHash(key) : dbIndex;
	}

	private int consistentHash(String key) {
		if (null != consistentHash) {
			return consistentHash.get(key);
		}
		return 0;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean setHash(final String key, Map<String, String> m) {
		if (m.isEmpty()) {
			return false;
		}
		try {

			final byte[] rawKey = rawKey(key);

			final Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>(
					m.size());

			for (Entry<String, String> entry : m.entrySet()) {
				hashes.put(rawKey(entry.getKey()), rawValue(entry.getValue()));
			}

			redisTemplate.execute(new RedisCallback<Object>() {

				public Object doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					connection.hMSet(rawKey, hashes);
					return null;
				}
			}, true);

			return true;
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	@Override
	public boolean setHash(final String key, String hashKey, String hashValue) {
		try {
			final byte[] rawKey = rawKey(key);
			final byte[] rawHashKey = rawKey(hashKey);
			final byte[] rawHashValue = rawValue(hashValue);

			return redisTemplate.execute(new RedisCallback<Boolean>() {

				public Boolean doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					return connection.hSet(rawKey, rawHashKey, rawHashValue);
				}
			}, true);

		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	@Override
	public String getHash(final String key, String hashKey) {
		try {
			if (StringUtils.isBlank(key) || StringUtils.isBlank(hashKey)) {
				return null;
			}
			final byte[] rawKey = rawKey(key);
			final byte[] rawHashKey = rawHashKey(hashKey);

			byte[] rawHashValue = this.redisTemplate.execute(
					new RedisCallback<byte[]>() {

						public byte[] doInRedis(RedisConnection connection) {
							connection.select(chooseDb(key));
							return connection.hGet(rawKey, rawHashKey);
						}
					}, true);

			return deserializeValue(rawHashValue);

		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	/**
	 * @Description:
	 * @param hashKey
	 * @return byte[]
	 * @throws
	 */
	private byte[] rawHashKey(String hashKey) {
		return this.rawKey(hashKey);
	}

	public RedisSerializer<String> getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(RedisSerializer<String> keySerializer) {
		this.keySerializer = keySerializer;
	}

	@Override
	public boolean removeHash(final String key, String hashKey) {
		try {

			final byte[] rawKey = rawKey(key);
			final byte[] rawHashKey = rawHashKey(hashKey);

			this.redisTemplate.execute(new RedisCallback<Object>() {

				public Object doInRedis(RedisConnection connection) {
					connection.select(chooseDb(key));
					connection.hDel(rawKey, rawHashKey);
					return null;
				}
			}, true);
			return true;
		} catch (Exception e) {
			LOGGER.error("redis throw exception", e);
			throw new CacheException(e);
		}
	}

	public Integer getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(Integer dbIndex) {
		this.dbIndex = dbIndex;
	}

	public List<Integer> getDbNodes() {
		return dbNodes;
	}

	public void setDbNodes(List<Integer> dbNodes) {
		this.dbNodes = dbNodes;
	}

}
