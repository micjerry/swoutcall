package com.lige.call.impl.beans;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallOperate;
import com.lige.call.api.exe.SwCallSwitchEvent;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.permission.PermissionControler;

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
	public List<SwCallReceipt> handleOperation(SwCallOperate operation) {
		if (PermissionControler.isPermited(operation, callTask)) {
			List<SwCallReceipt> commands = new ArrayList<>();
			logger.error("opid: id {} user: {} is not permitted , permited is false", operation.getId(), operation.getUser());
			return commands;
		}
		
		
		SwCallOperateHandler handler = callTask.getOptHandler(operation.getUser());
		if (handler == null) {
			logger.error("opid: id {} user: {} command {} is not permitted , permited is false", operation.getId(), operation.getUser(), operation.getCommand());
			return null;
		}

        return handler.handleOperation(operation, callTask);

	}
	
	
	@Override
	public List<SwCallReceipt> handleSwitchEvent(SwCallSwitchEvent event) {
		SwCallSwitchEventHandler handler = callTask.getMediaEventHandler(event.getEventName());
		
		if (handler == null) {
			logger.error("eventide: uid {} event: name {} of conference {} can not be handle", event.getId(), event.getEventName(), callTask.getId());
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
		logger.info("timertasks size111:{}",timertasks.size());
		Iterator<String> iterator = timertasks.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			SwCallTimerTask timerTask = timertasks.get(key);
			logger.info("key:{} task is executed:{} is valid:{}",key,timerTask.isExecuted(),timerTask.isValid());
			if (timerTask.isValid() && !timerTask.isExecuted()) {
				List<SwCallReceipt> result = timerTask.run();
				if (result != null) {
					commands.addAll(result);
				}
				logger.info("task:{} result:{}",key,result);
			}else {
				logger.info("remove task:{}",key);
				iterator.remove();
				timertasks.remove(key);
				continue;
			}
		}
		return commands;
		
	}
}
