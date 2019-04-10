package com.lige.call.impl.operatehandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lige.call.impl.api.SwCallOperateHandler;

public class OptHandlerFactory {
	public static Map<String, SwCallOperateHandler> getCallOptHandlers() {
		List<SwCallOperateHandler> optHandlers = new ArrayList<SwCallOperateHandler>();
		optHandlers.add(new SwCallOptHandlerHangup());
		optHandlers.add(new SwCallOptHandlerTransfer());
		
		HashMap<String, SwCallOperateHandler> optHandlersMap = new HashMap<String, SwCallOperateHandler>();
		
		for (SwCallOperateHandler handler: optHandlers) {
			optHandlersMap.put(handler.getName(), handler);
		}
		
		return optHandlersMap;
	}

}
