package com.lige.call.api.exe;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

public interface SwCallExecutor {
	
	public List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo oper);
	
	public List<SwCallReceipt> handleSwitchEvent(SwCommonCallEslEventPojo event);
	
	public List<SwCallReceipt> handleTimer();
	
}

