package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

class SwEventHandlerChannelCreate implements SwCallSwitchEventHandler {

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		NextNodeUtil.refreshChannel(SwCallState.RINGING, task, event);
		return null;
	}

	@Override
	public String getName() {
		return SwCommonCallEslConstant.ESLEVENT_CHANNEL_CREATE;
	}

}
