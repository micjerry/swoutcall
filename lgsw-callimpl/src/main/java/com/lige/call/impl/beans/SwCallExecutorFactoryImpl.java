package com.lige.call.impl.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerManager;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerManager;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

@Component
public class SwCallExecutorFactoryImpl implements SwCallExecutorFactory {
	@Autowired
	private SwCallOptHandlerManager optHandlerManager;
	
	@Autowired
	private SwEventHandlerManager eventHandlerManager;
	
	@Override
	public SwCallExecutor create(SwCommonCallSessionCreatePojo req) {
		
		SwCallTaskImpl taskImpl = new SwCallTaskImpl(req, optHandlerManager.createHandlers(), 
				eventHandlerManager.createHandlers());
		
		return  new SwCallExecutorImpl(taskImpl);
	}
}
