package com.lige.call.impl.beans;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptsys.SwCallSysReceiptFactory;

class SwCallTimeoutAll implements SwCallTimerTask{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
	SwCallTimeoutAll(SwCallTaskImpl task) {
		this.task = task;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return false;
	}

	@Override
	public List<SwCallReceipt> run() {
		if (task.getAssistImpl().isForceEnd()) {
			logger.info("Call Task {} force expired, hangup it", task);
			List<SwCallReceipt> commands = new ArrayList<>();
			commands.add(SwCallSysReceiptFactory.createForceEndCmd(task));
			return commands;
		}
		
		return null;
	}

}
