package com.lige.call.impl.receiptsys;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.cmd.SwCallReceiptSys;
import com.lige.call.impl.api.SwCallTask;

public class SwCallSysReceiptFactory {
	public static SwCallReceiptSys createForceEndCmd(SwCallTask task) {
		SwCallStopReceipt cmd = new SwCallStopReceipt(task.getId(), SwCallReceipt.RECEIPT_SYS_CALLEND);
		return cmd;
	}
}
