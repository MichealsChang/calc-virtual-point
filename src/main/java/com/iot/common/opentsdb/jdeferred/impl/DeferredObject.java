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
package com.iot.common.opentsdb.jdeferred.impl;

import com.iot.common.opentsdb.jdeferred.Deferred;
import com.iot.common.opentsdb.jdeferred.Promise;


public class DeferredObject<D, F, P> extends AbstractPromise<D, F, P> implements Deferred<D, F, P> {
	
	@Override
	public Deferred<D, F, P> resolve(final D resolve) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot resolve again");
			
			this.state = State.RESOLVED;
			this.resolveResult = resolve;
			
			try {
				triggerDone(resolve);
			} finally {
				triggerAlways(state, resolve, null);
			}
		}
		return this;
	}

	@Override
	public Deferred<D, F, P> notify(final P progress) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot notify progress");
			
			triggerProgress(progress);
		}
		return this;
	}

	@Override
	public Deferred<D, F, P> reject(final F reject) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot reject again");
			this.state = State.REJECTED;
			this.rejectResult = reject;
			
			try {
				triggerFail(reject);
			} finally {
				triggerAlways(state, null, reject);
			}
		}
		return this;
	}

	public Promise<D, F, P> promise() {
		return this;
	}
}
