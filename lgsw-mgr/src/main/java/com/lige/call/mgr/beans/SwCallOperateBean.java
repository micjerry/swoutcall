package com.lige.call.mgr.beans;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.mgr.routes.SwCallOperatePojo;

public class SwCallOperateBean implements Processor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallExecutor control;
	
	public SwCallOperateBean(SwCallExecutor control) {
		this.control = control;
	}
	
	public void process(Exchange exchange) throws Exception {

		SwCallOperatePojo operpojo = (SwCallOperatePojo)exchange.getIn().getBody();

		if (null == operpojo) {
			logger.error("There is no oper");
			return;
		} 

		logger.info("@@@receive conference control req {}",operpojo);

		SwCallOperateImpl operation = new SwCallOperateImpl(operpojo);
		logger.info("handle operation:{}", operation);
		
		List<SwCallReceipt> commands = control.handleOperation(operation);
		
		if (commands == null) {
			logger.info("There is no command for operation:{}", operation);
			return;
		}
		
		List<Message> msglist = CommandFormater.format(commands);;
		exchange.getIn().setBody(msglist);
	}
}
