package com.lige.call.api.cmd;

public abstract class SwCallReceiptSwitch implements SwCallReceipt{
	public final SubReceiptType getType() {
		return SubReceiptType.RECEIPT_TYPE_SWITCH;
	}
}
