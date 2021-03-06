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
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class DeferredFutureTask<D, P> extends FutureTask<D> {
	protected final Deferred<D, Throwable, P> deferred;
	protected final StartPolicy startPolicy;
	
	public DeferredFutureTask(Callable<D> callable) {
		super(callable);
		this.deferred = new DeferredObject<D, Throwable, P>();
		this.startPolicy = StartPolicy.DEFAULT;
	}
	
	public DeferredFutureTask(Runnable runnable) {
		super(runnable, null);
		this.deferred = new DeferredObject<D, Throwable, P>();
		this.startPolicy = StartPolicy.DEFAULT;
	}
	
	public DeferredFutureTask(DeferredCallable<D, P> callable) {
		super(callable);
		this.deferred = callable.getDeferred();
		this.startPolicy = callable.getStartPolicy();
	}
	
	@SuppressWarnings("unchecked")
	public DeferredFutureTask(DeferredRunnable<P> runnable) {
		super(runnable, null);
		this.deferred = (Deferred<D, Throwable, P>) runnable.getDeferred();
		this.startPolicy = runnable.getStartPolicy();
	}
	
	public Promise<D, Throwable, P> promise() {
		return deferred.promise();
	}
	
	@Override
	protected void done() {
		try {
			if (isCancelled()) {
				deferred.reject(new CancellationException());
				return;
			}
			D result = get();
			deferred.resolve(result);
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
			deferred.reject(e.getCause());
		}
	}

	public StartPolicy getStartPolicy() {
		return startPolicy;
	}
}
