package com.iot.calcvirtualpoint.common.runtime;

import com.exue.framework.dto.api.ApiReq;
import com.exue.framework.dto.api.ApiResp;


public class ApiHolder {

	private static ThreadLocal<ApiObject> threadLocal = new ThreadLocal<ApiObject>() {
		@Override
		protected ApiObject initialValue() {
			return new ApiObject();
		}
	};

	public static ApiReq getApiReq() {
		return threadLocal.get().getApiReq();
	}

	public static void setApiReq(ApiReq apiReq) {
		threadLocal.get().setApiReq(apiReq);
	}

	public static ApiResp getApiResp(){
		return threadLocal.get().getApiResp();
	}
	
	public static void setApiResp(ApiResp apiResp) {
		threadLocal.get().setApiResp(apiResp);
	}
	
	public static void cleanLocal() {
		threadLocal.remove();
	}

	public static class ApiObject {

		private ApiReq apiReq = new ApiReq();
		
		private ApiResp apiResp = new ApiResp();

		public ApiReq getApiReq() {
			return apiReq;
		}

		public void setApiReq(ApiReq apiReq) {
			this.apiReq = apiReq;
		}

		public ApiResp getApiResp() {
			return apiResp;
		}

		public void setApiResp(ApiResp apiResp) {
			this.apiResp = apiResp;
		}

		
	}

}
