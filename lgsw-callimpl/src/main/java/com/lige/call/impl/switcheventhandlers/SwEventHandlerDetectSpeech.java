package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.tools.DecodeSpeechDetectResult;
import com.lige.call.impl.tools.SpeechParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerDetectSpeech implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		DecodeSpeechDetectResult result = SpeechParser.parseSpeech(event.getEventBody());
		String detected =  "*";
		
		if (null == result) {
			logger.error("Can not parse detect result");
		} else {
			detected = result.getInterpretation().getInstance();
		}
		
		logger.info("task: {} node: {} speech detected: {}", task.getId(), NextNodeUtil.getNodeName(task), detected);
		task.getCurNode().setDetected(detected);
		
		return NextNodeUtil.nextStep(task);
	}

}
