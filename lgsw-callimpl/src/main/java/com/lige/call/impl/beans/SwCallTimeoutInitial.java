package com.lige.call.impl.beans;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallTask.CallStage;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;

class SwCallTimeoutInitial implements SwCallTimerTask{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
	SwCallTimeoutInitial(SwCallTaskImpl task) {
		this.task = task;
	}

	@Override
	public boolean isValid() {
		if (task.getAssistImpl().isCmdsend())
			return false;
		return true;
	}

	@Override
	public boolean isExecuted() {
		return false;
	}

	@Override
	public List<SwCallReceipt> run() {
		logger.info("Call Task {} initial", task);
		List<SwCallReceipt> commands = new ArrayList<>();
		commands.add(SwCallSwitchReceiptFactory.createCallCommand(task.getId(), 
				task.getCalleeNumber(), task.getCallerNumber(), task.getSwitchHost(), task.getGateway()));
		
		task.setStage(CallStage.CALL_STAGE_INITIAL);
		return commands;
		
	}

}
