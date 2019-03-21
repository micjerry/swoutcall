package com.lige.call.mgr.beans;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;

public class SwCallExecutorTimerBean implements Processor{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private SwCallExecutor control;

	public SwCallExecutorTimerBean(SwCallExecutor control) {
		this.control = control;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		List<SwCallReceipt> commands = control.handleTimer();
		if (commands != null) {
			for (SwCallReceipt conferenceSubCommand : commands) {
				logger.info("receive command:{}",conferenceSubCommand.getName());
			}
		}
		
		List<Message> msglist = SwCallReceiptFormater.format(commands);;
		exchange.getIn().setBody(msglist);
		
	}
}
