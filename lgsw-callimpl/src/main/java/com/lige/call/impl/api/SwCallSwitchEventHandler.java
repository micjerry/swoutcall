package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallSwitchEvent;

public interface SwCallSwitchEventHandler {
	public List<SwCallReceipt> handle(SwCallSwitchEvent event, SwCallTask task);
}
