package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwitchCallChannel.CallStage;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerChannelCreate implements SwCallSwitchEventHandler {

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		NextNodeUtil.refreshChannel(CallStage.CALL_STAGE_CREATED, task, event);
		return null;
	}

}
