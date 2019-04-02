package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptcdr.SwCallCdrReceiptFactory;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerPlayStop implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		
		String playFile = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_PLAY_FILE);
		SwCallPlayAndDetected playAndDetected = task.getChannel().getPlayAndDetected();
		
		if (null == playAndDetected || null == playFile) {
			logger.error("task: {} can not process play stop event no node found", task.getId());
			return null;
		}

		
		List<SwCallReceipt> results = ReceiptLoader.loadReceipt(SwCallCdrReceiptFactory.makeCallDialogPlayCdr(task));
		
		if (playFile.contains(playAndDetected.getFileName())) {
			logger.info("task: {} node: {} file: {} play finished", task.getId(), NextNodeUtil.getNodeName(task), playAndDetected.getFileName());
			playAndDetected.setPlayFinished(true);
			if (playAndDetected.isHangupAfterPlay()) {
				logger.info("task: {} node: {} hangup", task.getId(), NextNodeUtil.getNodeName(task));
				results.add(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
				return results;
			}
			
			return NextNodeUtil.nextStep(task, results);
		} else {
			logger.error("task: {} node: {} unkown play stop event filename {}",  task.getId(),  NextNodeUtil.getNodeName(task), playFile);
		}
		
		return null;
	}

}
