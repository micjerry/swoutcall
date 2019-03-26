package com.lige.call.impl.switcheventhandlers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;

@Component
public class EventHandlerFactory {
	@Autowired
	private SwEventHandlerChannelAnswer channelAnswerHandler;
	
	@Autowired
	private SwEventHandlerChannelCreate channelCreateHandler;
	
	@Autowired
	private SwEventHandlerChannelDestroy channelDestroyHandler;
	
	@Autowired
	private SwEventHandlerDetectSpeech detectSpeechHandler;
	
	@Autowired
	private SwEventHandlerPlayStop playStopHandler;
	
	@Autowired
	private SwEventHandlerPlayStart playStartHandler;
	
	@Autowired
	private SwEventHandlerRecordStart recordStarthandler;
	
	@Autowired
	private SwEventHandlerRecordStop recordStopHandler;
	
	private HashMap<String, SwCallSwitchEventHandler> callHandlers;
	
	public void initialHandlers() {
		callHandlers = new HashMap<String, SwCallSwitchEventHandler>();
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_CREATE, channelCreateHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_ANSWER, channelAnswerHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_CHANNEL_DESTROY, channelDestroyHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_DETECTED_SPEECH, detectSpeechHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_PLAYBACK_STOP, playStopHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_PLAYBACK_START, playStartHandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_RECORD_START, recordStarthandler);
		callHandlers.put(SwCommonCallEslConstant.ESLEVENT_RECORD_STOP, recordStopHandler);
	}
	
	
	public HashMap<String, SwCallSwitchEventHandler> createCallHandlers() {
		return callHandlers;
	}
}
