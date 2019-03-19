package com.lige.call.impl.operatehandlers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.impl.api.SwCallOperateHandler;

@Component
public class SwCallOptHandlerManager {
	
	@Autowired 
	private SwCallOptHandlerHangup hangupHandler;
	
	@Autowired 
	private SwCallOptHandlerTransfer transferHandler;
	
	public HashMap<String, SwCallOperateHandler> createHandlers() {
		HashMap<String, SwCallOperateHandler> handlers = new HashMap<String, SwCallOperateHandler>();
		
		handlers.put(SwCallOperateHandler.OPER_HANGUP, hangupHandler);
		handlers.put(SwCallOperateHandler.OPER_TRANSFER, transferHandler);
		
		return handlers;
	}

}
