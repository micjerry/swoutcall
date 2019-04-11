package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;

public interface SwCallTimerTask {	
	public String getName();
	
	public boolean isValid();
	
	public boolean isReady();
	
	public List<SwCallReceipt> run();
	
}
