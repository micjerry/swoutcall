package com.lige.call.impl.beans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.receiptsys.SwCallSysReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.cdr.SwCommonCallCdrConstant;

class SwCallTimeoutState implements SwCallTimerTask {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String name;
	private SwCallTaskImpl task;
	private int stageExpireLimit;
	private boolean executed;
	private SwCallState state;

	public SwCallTimeoutState(String name, SwCallTaskImpl task, int stageExpireLimit) {
		this.name = name;
		this.task = task;
		this.stageExpireLimit = stageExpireLimit;
		this.executed = false;
		this.state = task.getChannel().getCallState();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid() {
		return (this.state == task.getChannel().getCallState());
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public List<SwCallReceipt> run() {
		if (this.stageExpireLimit-- < 0) {
			logger.info("call: {} timer: {} triggered.", task.getId(), name);
			this.executed = true;	
			switch (task.getChannel().getCallState()) {
			case NONE:
				task.getChannel().setHangupCause(SwCommonCallCdrConstant.CDRFIELD_HANGUPCAUSE_ENUM_CREATEFAIL);
				return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));
			case CREATING:
				task.getChannel().setHangupCause(SwCommonCallCdrConstant.CDRFIELD_HANGUPCAUSE_ENUM_CREATEFAIL);
				return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));
			case CALLING:
				task.getChannel().setHangupCause(SwCommonCallCdrConstant.CDRFIELD_HANGUPCAUSE_ENUM_RUNEXPIRED);
				return ReceiptLoader
						.loadReceipt(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
			case HANGING:
				task.getChannel().setHangupCause(SwCommonCallCdrConstant.CDRFIELD_HANGUPCAUSE_ENUM_NORMAL);
				return ReceiptLoader.loadReceipt(SwCallSysReceiptFactory.createForceEndCmd(task));
			case RINGING:
				task.getChannel().setHangupCause(SwCommonCallCdrConstant.CDRFIELD_HANGUPCAUSE_ENUM_RINGEXPIRED);
				return ReceiptLoader
						.loadReceipt(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
			default:
				break;
			}
		}

		return null;
	}

}
