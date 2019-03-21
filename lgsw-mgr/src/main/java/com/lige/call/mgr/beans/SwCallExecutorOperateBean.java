package com.lige.call.mgr.beans;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

public class SwCallExecutorOperateBean implements Processor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallExecutor control;
	
	public SwCallExecutorOperateBean(SwCallExecutor control) {
		this.control = control;
	}
	
	public void process(Exchange exchange) throws Exception {

		SwCommonCallSessionOperPojo operpojo = (SwCommonCallSessionOperPojo)exchange.getIn().getBody();

		if (null == operpojo) {
			logger.error("There is no oper");
			return;
		} 

		logger.info("@@@receive call operate req {}",operpojo);

		List<SwCallReceipt> commands = control.handleOperation(operpojo);
		
		if (commands == null) {
			logger.info("There is no command for operation:{}", operpojo);
			return;
		}
		
		List<Message> msglist = SwCallReceiptFormater.format(commands);;
		exchange.getIn().setBody(msglist);
	}
}
