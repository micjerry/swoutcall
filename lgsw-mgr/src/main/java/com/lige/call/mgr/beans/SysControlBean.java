package com.lige.call.mgr.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceiptSys;
import com.lige.call.mgr.utils.SwCallStopExecutorService;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;

public class SysControlBean implements Processor{

	private final static Logger logger = LoggerFactory.getLogger(SysControlBean.class);

	private String callId;
	
	public SysControlBean(String callId) {
		this.callId = callId;
	}
	
	public void process(Exchange exchange) throws Exception {

		logger.info("process is called....");

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

		if (syscomand.getName().equalsIgnoreCase(SwCommonCallEslConstant.ESLEVENT_CHANNEL_DESTROY)) {
			SwCallStopBean stopBean = new SwCallStopBean(exchange.getContext(), callId);
			SwCallStopExecutorService.getExecutor().submit(stopBean);
		}
	}
}
