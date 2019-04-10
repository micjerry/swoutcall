package com.lige.call.impl.operatehandlers;

import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

class SwCallOptHandlerTransfer implements SwCallOperateHandler {

	@Override
	public List<SwCallReceipt> handleOperation(SwCommonCallSessionOperPojo operation, SwCallTask task) {
		return null;
	}

	@Override
	public String getName() {
		return SwCommonCallOperConstant.CALLSESSION_OPERNAME_TRANSFER;
	}

}
