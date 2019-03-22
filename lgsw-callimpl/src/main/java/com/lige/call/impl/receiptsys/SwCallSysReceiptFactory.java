package com.lige.call.impl.receiptsys;

import com.lige.call.api.cmd.SwCallReceiptSys;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;

public class SwCallSysReceiptFactory {
	public static SwCallReceiptSys createForceEndCmd(SwCallTask task) {
		SwCallStopReceipt cmd = new SwCallStopReceipt(task.getId(), SwCommonCallEslConstant.ESLEVENT_CHANNEL_DESTROY);
		return cmd;
	}
}
