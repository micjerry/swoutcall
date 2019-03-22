package com.lige.call.impl.beans;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallConstant;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.api.SwitchCallChannel.CallStage;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;

class SwCallTimeoutRinging implements SwCallTimerTask{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
	public SwCallTimeoutRinging(SwCallTaskImpl task) {
		this.task = task;
	}

	@Override
	public boolean isValid() {
		if (task.getChannel().testStage(CallStage.CALL_STAGE_ANSWERED))
			return false;
		return true;
	}

	@Override
	public boolean isExecuted() {
		return false;
	}

	@Override
	public List<SwCallReceipt> run() {
		if (task.getChannel().isRingExpired()) {
			logger.info("Call Task {} ring expired, hangup it", task);
			List<SwCallReceipt> commands = new ArrayList<>();
			commands.add(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), SwCallConstant.HANGUP_CAUSE_EXPIRED));
			return commands;
		}
		
		return null;
	}

}
