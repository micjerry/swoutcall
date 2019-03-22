package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwitchCallChannel.CallStage;
import com.lige.call.impl.receiptsys.SwCallSysReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public class SwEventHandlerChannelDestroy implements SwCallSwitchEventHandler {

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		NextNodeUtil.refreshChannel(CallStage.CALL_STAGE_HANGUP, task, event);
		return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));
	}

}
