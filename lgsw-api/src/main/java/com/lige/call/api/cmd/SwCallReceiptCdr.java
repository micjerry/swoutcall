package com.lige.call.api.cmd;

public abstract class SwCallReceiptCdr implements SwCallReceipt {
	public final SubReceiptType getType() {
		return SubReceiptType.RECEIPT_TYPE_CDR;
	}
}
