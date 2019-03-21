package com.lige.call.api.cmd;


public abstract class SwCallReceiptHttp implements SwCallReceipt {	
	public final SubReceiptType getType() {
		return SubReceiptType.RECEIPT_TYPE_HTTP;
	}
	
	public abstract String getUrl();
	
	public abstract String getMethod();
	
	public abstract String getHost();
}
