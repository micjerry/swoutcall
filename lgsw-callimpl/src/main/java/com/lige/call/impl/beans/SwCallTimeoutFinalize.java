package com.lige.call.impl.beans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptsys.SwCallSysReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;

class SwCallTimeoutFinalize implements SwCallTimerTask {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SwCallTaskImpl task;

	private boolean executed;

	private String name;

	private int callLimit;

	public SwCallTimeoutFinalize(String name, SwCallTaskImpl task, int callLimit) {
		this.task = task;
		this.executed = false;
		this.name = name;
		this.callLimit = callLimit;
	}

	@Override
	public List<SwCallReceipt> run() {
		if (this.callLimit-- < 0) {
			logger.info("call: {} timer: {} triggered.", task.getId(), name);
			executed = true;
			return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));
		}
		
		return null;

	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public String getName() {
		return name;
	}

}
