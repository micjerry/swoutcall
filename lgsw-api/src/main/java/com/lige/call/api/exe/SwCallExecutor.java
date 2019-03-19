package com.lige.call.api.exe;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;

public interface SwCallExecutor {
	
	public List<SwCallReceipt> handleOperation(SwCallOperate operation);
	
	public List<SwCallReceipt> handleSwitchEvent(SwCallSwitchEvent event);
	
	public List<SwCallReceipt> handleTimer();
	
}

