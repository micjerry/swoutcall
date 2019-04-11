package com.lige.call.impl.beans;

import java.util.ArrayList;
import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;

class SwCallTimeoutInitial implements SwCallTimerTask{
	
	private SwCallTaskImpl task;
	
	private String name;
	
	SwCallTimeoutInitial(String name, SwCallTaskImpl task) {
		this.task = task;
		this.name = name;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public List<SwCallReceipt> run() {
		List<SwCallReceipt> commands = new ArrayList<>();
		commands.add(SwCallSwitchReceiptFactory.createCallCommand(task.getId(), 
				task.getCalleeNumber(), task.getCallerNumber(), task.getSwitchHost(), task.getGateway()));
		
		SwitchCallChannel channel = task.getChannel();
		channel.setCallState(SwCallState.CREATING, null);
		return commands;
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isReady() {
		return true;
	}

}
