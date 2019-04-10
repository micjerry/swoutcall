package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public interface SwCallSwitchEventHandler {
	public String getName();
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task);
}
