package com.lige.call.impl.operatehandlers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;

@Component
public class OptHandlerFactory {
	
	@Autowired 
	private SwCallOptHandlerHangup hangupHandler;
	
	@Autowired 
	private SwCallOptHandlerTransfer transferHandler;
	
	private HashMap<String, SwCallOperateHandler> callOpthandlers;
	
	public void initialHandlers() {
		callOpthandlers  = new HashMap<String, SwCallOperateHandler>();
		
		callOpthandlers.put(SwCommonCallOperConstant.CALLSESSION_OPERNAME_HANGUP, hangupHandler);
		callOpthandlers.put(SwCommonCallOperConstant.CALLSESSION_OPERNAME_TRANSFER, transferHandler);
	}
	
	public HashMap<String, SwCallOperateHandler> createCallHandlers() {
		return callOpthandlers;
	}

}
