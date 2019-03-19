package com.lige.call.api.exe;

import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public interface SwCallExecutorFactory {
	public SwCallExecutor create(SwCommonCallSessionCreatePojo req);
}
