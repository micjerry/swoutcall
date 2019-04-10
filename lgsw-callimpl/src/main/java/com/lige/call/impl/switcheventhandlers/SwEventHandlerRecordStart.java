package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

class SwEventHandlerRecordStart implements SwCallSwitchEventHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		String fileName = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_RECORD_FILE);
		if (null == fileName || "".equals(fileName)) {
			logger.error("task: {} invalid record start event", task.getId());
			return null;
		}
		
		logger.info("task: {} record start event received file: {} ", task.getId(), fileName);
		
		task.getChannel().setRecordFile(fileName);
		
		return null;
		
	}

	@Override
	public String getName() {
		return SwCommonCallEslConstant.ESLEVENT_RECORD_START;
	}

}
