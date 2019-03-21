package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

public interface SwCallOperateHandler {
	//public static final String OPER_SWITCHMODE = "switch_mode";
	String OPER_HANGUP = "hangup";
	String OPER_TRANSFER = "transfer";
	String OPER_MUTE = "mute";
	List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo operation, SwCallTask task);
}