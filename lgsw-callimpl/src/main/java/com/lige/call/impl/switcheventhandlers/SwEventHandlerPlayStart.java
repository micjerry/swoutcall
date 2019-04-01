package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerPlayStart implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		String playFile = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_PLAY_FILE);
		SwCallPlayAndDetected playAndDetected = task.getChannel().getPlayAndDetected();
		
		if (null == playAndDetected || null == playFile) {
			logger.error("can not process play start event no node found");
			return null;
		}
		
		if (playFile.contains(playAndDetected.getFileName())) {
			logger.info("task: {} node: {} file: {} start to play", task.getId(), NextNodeUtil.getNodeName(task), playAndDetected.getFileName());
			playAndDetected.setPlayStarted(true);
		} else {
			logger.error("unkown play start event");
		}
		
		return null;
	}

}
