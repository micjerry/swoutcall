package com.lige.call.impl.switcheventhandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lige.call.impl.api.SwCallSwitchEventHandler;

public class EventHandlerFactory {
	public static Map<String, SwCallSwitchEventHandler> getCallEventHandlers() {
		List<SwCallSwitchEventHandler> eventHandlers = new ArrayList<SwCallSwitchEventHandler>();
		eventHandlers.add(new SwEventHandlerChannelAnswer());
		eventHandlers.add(new SwEventHandlerChannelCreate());
		eventHandlers.add(new SwEventHandlerChannelDestroy());
		eventHandlers.add(new SwEventHandlerDetectSpeech());
		eventHandlers.add(new SwEventHandlerPlayStop());
		eventHandlers.add(new SwEventHandlerPlayStart());
		eventHandlers.add(new SwEventHandlerRecordStart());
		eventHandlers.add(new SwEventHandlerRecordStop());
	
		
		HashMap<String, SwCallSwitchEventHandler> eventHandlersMap = new HashMap<String, SwCallSwitchEventHandler>();
		for (SwCallSwitchEventHandler handler: eventHandlers) {
			eventHandlersMap.put(handler.getName(), handler);
		}
		
		return eventHandlersMap;
	}
}
