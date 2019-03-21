package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTask.CallStage;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

@Component
public class SwEventChannelDestroyHandler implements SwCallSwitchEventHandler {

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		task.setStage(CallStage.CALL_STAGE_HANGUP);
		return null;
	}

}
