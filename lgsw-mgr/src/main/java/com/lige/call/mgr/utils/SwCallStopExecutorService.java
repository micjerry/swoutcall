package com.lige.call.mgr.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SwCallStopExecutorService {

	private static ScheduledExecutorService executor = null;
	
	public synchronized static ScheduledExecutorService getExecutor() {
		if (executor == null) {
			executor = Executors.newScheduledThreadPool(5);
		}
		
		return executor;
	}
	
}
