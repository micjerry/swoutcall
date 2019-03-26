package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptcdr.SwCallCdrReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerRecordStop implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		String fileName = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_RECORD_FILE);
		String recordMslength = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_RECORD_MSLENGTH);
		if (null == fileName || "".equals(fileName)) {
			logger.error("call: {} invalid record start event", task.getId());
			return null;
		}
		
		logger.info("call: {} record start event received file: {} length: {}ms ", task.getId(), fileName, recordMslength);
		
		task.getChannel().setRecordFile(fileName);
		task.getChannel().setRecordMsLength(recordMslength);
		
		return ReceiptLoader.loadReceipt(SwCallCdrReceiptFactory.makeCallRecordCdr(task));
	}

}
