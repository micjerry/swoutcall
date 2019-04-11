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
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTimerTask;
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
		SwCallOperateHandler handler = callTask.getOptHandler(operation.getName());
		if (handler == null) {
			logger.error("task: {} operate: {} is not permitted , permited is false", callTask.getId(),
					operation.getName());
			return null;
		}

		SwCallState preState = callTask.getChannel().getCallState();
		List<SwCallReceipt> commands = null;
		synchronized (callTask) {
			commands = handler.handleOperation(operation, callTask);
		}

		callTask.loadNewTimer(preState);
		return commands;

	}

	@Override
	public List<SwCallReceipt> handleSwitchEvent(SwCommonCallEslEventPojo event) {
		SwCallSwitchEventHandler handler = callTask.getSwitchEventHandler(SwCommonCallEslEventParser.getName(event));

		if (handler == null) {
			logger.error("task: {} event: {} can not be handle", callTask.getId(),
					SwCommonCallEslEventParser.getName(event));
			return null;
		}

		SwCallState preState = callTask.getChannel().getCallState();
		List<SwCallReceipt> commands = null;

		synchronized (callTask) {
			commands = handler.handle(event, callTask);
		}

		callTask.loadNewTimer(preState);

		return commands;
	}

	@Override
	public List<SwCallReceipt> handleTimer() {
		SwCallState preState = callTask.getChannel().getCallState();
		List<SwCallReceipt> commands = null;
		Map<String, SwCallTimerTask> timertasks = callTask.getTimerTasks();
		Iterator<String> iterator = timertasks.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			SwCallTimerTask timerTask = timertasks.get(key);
			if (timerTask.isValid()) {
				if (timerTask.isReady()) {
					synchronized (callTask) {
						List<SwCallReceipt> result = timerTask.run();
						if (result != null) {
							if (null == commands)
								commands = new ArrayList<>();

							commands.addAll(result);
						}
						
						timertasks.remove(key);
						logger.info("task: {} timer:{} executed", callTask.getId(), key);
					}
				}
			} else {
				logger.info("task: {} remove timer:{}", callTask.getId(), key);
				timertasks.remove(key);
				continue;
			}
		}

		callTask.loadNewTimer(preState);

		return commands;

	}
}
