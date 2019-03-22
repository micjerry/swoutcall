package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

public interface SwCallOperateHandler {
	List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo operation, SwCallTask task);
}