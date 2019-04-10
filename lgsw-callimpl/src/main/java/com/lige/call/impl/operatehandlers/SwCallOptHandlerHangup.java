package com.lige.call.impl.operatehandlers;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

class SwCallOptHandlerHangup implements SwCallOperateHandler {

	@Override
	public List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo operation, SwCallTask task) {
		return ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
	}

	@Override
	public String getName() {
		return SwCommonCallOperConstant.CALLSESSION_OPERNAME_HANGUP;
	}

}
