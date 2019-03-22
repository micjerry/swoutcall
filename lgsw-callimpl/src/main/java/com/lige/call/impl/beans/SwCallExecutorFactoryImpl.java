package com.lige.call.impl.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.operatehandlers.OptHandlerFactory;
import com.lige.call.impl.switcheventhandlers.EventHandlerFactory;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

@Component
public class SwCallExecutorFactoryImpl implements SwCallExecutorFactory {
	@Autowired
	private OptHandlerFactory optHandlerManager;
	
	@Autowired
	private EventHandlerFactory eventHandlerManager;
	
	@Override
	public SwCallExecutor create(SwCommonCallSessionCreatePojo req) {
		
		SwCallTaskImpl taskImpl = new SwCallTaskImpl(req, optHandlerManager.createCallHandlers(), 
				eventHandlerManager.createCallHandlers());
		
		return  new SwCallExecutorImpl(taskImpl);
	}
}
