package com.lige.call.impl.beans;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallConstant;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;

class SwCallTimeoutCalling implements SwCallTimerTask{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private SwCallTaskImpl task;
	
	public SwCallTimeoutCalling(SwCallTaskImpl task) {
		this.task = task;
	}
	
	@Override
	public  List<SwCallReceipt> run() {
		if (task.getChannel().isCallingExpired()) {
			logger.info("Call Task {} is expired, hangup it", task.getId());
			List<SwCallReceipt> commands = new ArrayList<>();
			commands.add(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), SwCallConstant.HANGUP_CAUSE_EXPIRED));
			return commands;
		}
		
		return null;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return false;
	}

}
