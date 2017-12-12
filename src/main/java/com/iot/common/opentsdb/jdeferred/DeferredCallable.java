/*
 * Copyright 2013-2016 Ray Tsang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iot.common.opentsdb.jdeferred;

import com.iot.common.opentsdb.jdeferred.DeferredManager.StartPolicy;
import com.iot.common.opentsdb.jdeferred.impl.DeferredObject;

import java.util.concurrent.Callable;


public abstract class DeferredCallable<D, P> implements Callable<D> {
	private final Deferred<D, Throwable, P> deferred = new DeferredObject<D, Throwable, P>();
	private final StartPolicy startPolicy;
	
	public DeferredCallable() {
		this.startPolicy = StartPolicy.DEFAULT;
	}
	
	public DeferredCallable(StartPolicy startPolicy) {
		this.startPolicy = startPolicy;
	}
	
	/**
	 * @see Deferred#notify(Object)
	 * @param progress
	 */
	protected void notify(P progress) {
		deferred.notify(progress);
	}
	
	protected Deferred<D, Throwable, P> getDeferred() {
		return deferred;
	}

	public StartPolicy getStartPolicy() {
		return startPolicy;
	}
}
