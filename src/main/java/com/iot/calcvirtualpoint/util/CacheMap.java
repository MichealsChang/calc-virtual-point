package com.iot.calcvirtualpoint.util;

import com.iot.calcvirtualpoint.common.util.LogUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheMap<K, V> extends AbstractMap<K, V> {

	private static final long DEFAULT_TIMEOUT = 60*1000;

	private static CacheMap<Object, Object> defaultInstance;

	public static synchronized final CacheMap<Object, Object> getDefault() {
		if (defaultInstance == null) {
			defaultInstance = new CacheMap<Object, Object>(DEFAULT_TIMEOUT);
		}
		return defaultInstance;
	}

	private class CacheEntry implements Entry<K, V> {
		long time;
		V value;
		K key;

		CacheEntry(K key, V value) {
			super();
			this.value = value;
			this.key = key;
			this.time = System.currentTimeMillis();
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}
	}

	private class ClearThread extends Thread {
		ClearThread() {
			setName("clear cache thread");
		}

		public void run() {
			while (true) {
				try {
					long now = System.currentTimeMillis();
					Object[] keys = map.keySet().toArray();
					for (Object key : keys) {
						CacheEntry entry = map.get(key);
						if (now - entry.time >= cacheTimeout) {
							synchronized (map) {
								LogUtils.debugLog("###################################");
								LogUtils.debugLog("map.remove("+key+")");
								map.remove(key);
								LogUtils.debugLog("###################################");
							}
						}
					}
					Thread.sleep(cacheTimeout);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private long cacheTimeout;
	private Map<K, CacheEntry> map = new HashMap<K, CacheEntry>();

	public CacheMap(long timeout) {
		this.cacheTimeout = timeout;
		new ClearThread().start();
	}

	@Override

	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();
		Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
		for (Entry<K, CacheEntry> entry : wrapEntrySet) {
			entrySet.add(entry.getValue());
		}
		return entrySet;
	}

	@Override
	public V get(Object key) {
		CacheEntry entry = map.get(key);
		return entry == null ? null : entry.value;
	}

	@Override

	public V put(K key, V value) {
		CacheEntry entry = new CacheEntry(key, value);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}

}
