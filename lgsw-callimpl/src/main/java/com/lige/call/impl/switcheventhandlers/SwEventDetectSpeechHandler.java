package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.DecodeSpeechDetectResult;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.call.impl.tools.SpeechParser;
import com.lige.call.impl.tools.SwCallSceneLogic;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

@Component
public class SwEventDetectSpeechHandler implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		DecodeSpeechDetectResult result = SpeechParser.parseSpeech(event.getEventBody());
		String detected =  null;
		
		if (null == result) {
			logger.error("Can not parse detect result");
			detected = "*";
		} else {
			detected = result.getInterpretation().getInstance();
		}
		
		SwCommonCallDialogNode nextNode = SwCallSceneLogic.getNextNode(task.getCurNode(), detected, task.getDialog());
		
		if (null == nextNode) {
			logger.error("Can not find next node hangup it");
			return  ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createHangupCommand(task.getUuid(), null));
		}
		
		task.setCurNode(nextNode);
		return ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task.getUuid(), nextNode));
	}

}
