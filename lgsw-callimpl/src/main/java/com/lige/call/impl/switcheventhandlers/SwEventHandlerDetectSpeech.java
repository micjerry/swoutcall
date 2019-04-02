package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptcdr.SwCallCdrReceiptFactory;
import com.lige.call.impl.tools.BaiduSpeechResult;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.call.impl.tools.SpeechParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerDetectSpeech implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		BaiduSpeechResult result = SpeechParser.parseSpeech(event.getEventBody());
		String detected =  "*";
		
		if (null == result) {
			logger.error("task: {} node: {} can not parse detect result", task.getId(), NextNodeUtil.getNodeName(task));
		} else {
			detected = result.getInterpretation().getInput();
		}
		
		logger.info("task: {} node: {} speech detected: {}", task.getId(), NextNodeUtil.getNodeName(task), detected);
		
		SwCallPlayAndDetected playAndDetected = task.getChannel().getPlayAndDetected();
		
		if (null == playAndDetected) {
			logger.error("task: {} node: {} no detected node found", task.getId(), NextNodeUtil.getNodeName(task));
			return null;
		}
		
		playAndDetected.setDetected(detected);
		
		List<SwCallReceipt> results = ReceiptLoader.loadReceipt(SwCallCdrReceiptFactory.makeCallDialogDetectCdr(task));
		
		return NextNodeUtil.nextStep(task, results);
	}

}
