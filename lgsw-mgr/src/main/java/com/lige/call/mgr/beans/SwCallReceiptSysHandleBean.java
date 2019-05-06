package com.lige.call.mgr.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.cmd.SwCallReceiptSys;
import com.lige.call.mgr.routes.SwCallContext;
import com.lige.call.mgr.scheduler.CallSchedulerMgr;
import com.lige.call.mgr.utils.SwCallExecutorService;

public class SwCallReceiptSysHandleBean implements Processor{

	private final static Logger logger = LoggerFactory.getLogger(SwCallReceiptSysHandleBean.class);

	private String callId;
	
	private String groupId;
	
	private SwCallContext context;
	
	public SwCallReceiptSysHandleBean(String callId, String groupId, SwCallContext context) {
		this.callId = callId;
		this.groupId = groupId;
		this.context = context;
	}
	
	public void process(Exchange exchange) throws Exception {
		SwCallReceiptSys syscomand = exchange.getIn().getBody(SwCallReceiptSys.class);
		boolean isNullSyscomand = (null == syscomand);
		if (!isNullSyscomand) {
			if (syscomand.getName() == null) {
				logger.error("syscomand.getName is null ");
				return;
			}
		}else {
			logger.error("syscomand is null");
			return;
		}

		if (syscomand.getName().equalsIgnoreCase(SwCallReceipt.RECEIPT_SYS_CALLEND)) {
			SwCallStopBean stopBean = new SwCallStopBean(exchange.getContext(), callId);
			SwCallExecutorService.getExecutor().submit(stopBean);
			
			CallSchedulerMgr.commitNextCall(groupId, context);
		}
	}
}
