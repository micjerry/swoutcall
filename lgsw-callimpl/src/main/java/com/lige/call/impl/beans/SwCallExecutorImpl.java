package com.lige.call.impl.beans;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.permission.PermissionControler;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

class SwCallExecutorImpl implements SwCallExecutor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private SwCallTask callTask;

	public SwCallExecutorImpl(SwCallTask callTask) {
		this.callTask = callTask;
	}
	
	@Override
	public String toString() {
		return callTask.toString();
	}
	
	@Override
	public List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo operation) {
		if (PermissionControler.isPermited(operation, callTask)) {
			List<SwCallReceipt> commands = new ArrayList<>();
			logger.error("opid: id {} name: {} is not permitted , permited is false", operation.getId(), operation.getName());
			return commands;
		}
		
		
		SwCallOperateHandler handler = callTask.getOptHandler(operation.getName());
		if (handler == null) {
			logger.error("opid: id {} name: {} command {} is not permitted , permited is false", operation.getId(), operation.getName(), operation.getCommand());
			return null;
		}

        return handler.handleOperation(operation, callTask);

	}
	
	
	@Override
	public List<SwCallReceipt> handleSwitchEvent(SwCommonCallEslEventPojo event) {
		SwCallSwitchEventHandler handler = callTask.getMediaEventHandler(SwCommonCallEslEventParser.getName(event));
		
		if (handler == null) {
			logger.error("eventide: uid {} event: name {} of conference {} can not be handle", 
					SwCommonCallEslEventParser.getSwitchChannelId(event), 
					SwCommonCallEslEventParser.getName(event), callTask.getId());
			return null;
		}
		
		List<SwCallReceipt> results = handler.handle(event, callTask);
		logger.info("results:{}",results);
		
		return results;
	}
	
	
	@Override
	public List<SwCallReceipt> handleTimer() {

		List<SwCallReceipt> commands = new ArrayList<>();
		Map<String, SwCallTimerTask> timertasks = callTask.getTimertasks();
		Iterator<String> iterator = timertasks.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			SwCallTimerTask timerTask = timertasks.get(key);
			if (timerTask.isValid() && !timerTask.isExecuted()) {
				List<SwCallReceipt> result = timerTask.run();
				if (result != null) {
					commands.addAll(result);
				}
				logger.info("task:{} executed",key);
			}else {
				logger.info("remove task:{}", key);
				iterator.remove();
				timertasks.remove(key);
				continue;
			}
		}
		return commands;
		
	}
}
