package com.lige.call.impl.beans;

import java.util.Map;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.operatehandlers.OptHandlerFactory;
import com.lige.call.impl.switcheventhandlers.EventHandlerFactory;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class SwCallExecutorFactoryImpl implements SwCallExecutorFactory {
	
	private Map<String, SwCallSwitchEventHandler> eventHandlers;
	
	private Map<String, SwCallOperateHandler> optHandlers;

	
	public SwCallExecutorFactoryImpl() {
		eventHandlers = EventHandlerFactory.getCallEventHandlers();
		optHandlers = OptHandlerFactory.getCallOptHandlers();
	}
	
	@Override
	public SwCallExecutor create(SwCommonCallSessionCreatePojo req) {
		
		SwCallTaskImpl taskImpl = new SwCallTaskImpl(req, optHandlers, eventHandlers);
		
		return  new SwCallExecutorImpl(taskImpl);
	}
	
}
