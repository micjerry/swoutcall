package com.lige.call.impl.api;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;

public interface SwCallTimerTask {	
	public  boolean isValid();
	
	public  boolean isExecuted();
	
	public  List<SwCallReceipt> run();
}
