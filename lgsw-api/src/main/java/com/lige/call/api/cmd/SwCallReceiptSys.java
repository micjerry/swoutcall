package com.lige.call.api.cmd;

public abstract class SwCallReceiptSys implements SwCallReceipt {
	
	public final SubCommandType getType() {
		return SubCommandType.COMMAND_TYPE_SYS;
	}
}
