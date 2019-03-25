package com.lige.call.impl.beans;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;

class SwCallTimeoutInitial implements SwCallTimerTask{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
	private boolean executed;
	
	private String name;
	
	SwCallTimeoutInitial(String name, SwCallTaskImpl task) {
		this.task = task;
		this.executed = false;
		this.name = name;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public List<SwCallReceipt> run() {
		logger.info("call: {} timer: {} triggered.", task.getId(), name);
		List<SwCallReceipt> commands = new ArrayList<>();
		commands.add(SwCallSwitchReceiptFactory.createCallCommand(task.getId(), 
				task.getCalleeNumber(), task.getCallerNumber(), task.getSwitchHost(), task.getGateway()));
		
		SwitchCallChannel channel = task.getChannel();
		channel.setCallState(SwCallState.CREATING, null);
		this.executed = true;
		return commands;
		
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

}
