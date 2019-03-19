package com.lige.call.mgr.beans;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwCallExecutorEventBean implements Processor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallExecutor executor;
	
	public SwCallExecutorEventBean(SwCallExecutor control) {
		this.executor = control;
	}
	
	public void process(Exchange exchange) throws Exception {
		SwCommonCallEslEventPojo eventpojo = (SwCommonCallEslEventPojo)exchange.getIn().getBody();
		
		logger.info("@@@received event from switch");
		SwCallSwitchEventImpl event = new SwCallSwitchEventImpl(eventpojo);
		List<SwCallReceipt> commands = executor.handleSwitchEvent(event);
		if (commands == null) {
			logger.info("There is no command for event: name {}", event.getEventName());
			return;
		}
		List<Message> msglist = CommandFormater.format(commands);
		
		exchange.getIn().setBody(msglist);
	}
}
