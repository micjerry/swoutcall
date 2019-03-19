package com.lige.call.impl.switcheventhandlers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;

@Component
public class SwEventHandlerManager {
	@Autowired
	private SwEventChannelAnswerHandler channelAnswerHandler;
	
	@Autowired
	private SwEventChannelCreateHandler channelCreateHandler;
	
	@Autowired
	private SwEventChannelDestroyHandler channelDestroyHandler;
	
	@Autowired
	private SwEventDetectSpeechHandler detectSpeechHandler;
	
	public HashMap<String, SwCallSwitchEventHandler> createHandlers() {
		HashMap<String, SwCallSwitchEventHandler> handlers = new HashMap<String, SwCallSwitchEventHandler>();
		handlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_CREATE, channelCreateHandler);
		handlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_ANSWER, channelAnswerHandler);
		handlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_DESTROY, channelDestroyHandler);
		handlers.put(SwCommonCallEslConstant.ESLEVENT_DETECTED_SPEECH, detectSpeechHandler);
		return handlers;
	}
}
