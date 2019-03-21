package com.lige.call.api.cmd;

public abstract class SwCallReceiptSys implements SwCallReceipt {
	
	public final SubReceiptType getType() {
		return SubReceiptType.RECEIPT_TYPE_SYS;
	}
}
