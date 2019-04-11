package com.lige.call.impl.beans;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptsys.SwCallSysReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;

class SwCallTimeoutFinalize implements SwCallTimerTask {

	private SwCallTaskImpl task;

	private String name;

	private int callLimit;

	public SwCallTimeoutFinalize(String name, SwCallTaskImpl task, int callLimit) {
		this.task = task;
		this.name = name;
		this.callLimit = callLimit;
	}

	@Override
	public List<SwCallReceipt> run() {
		return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));	
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isReady() {
		return (this.callLimit-- < 0);
	}

}
