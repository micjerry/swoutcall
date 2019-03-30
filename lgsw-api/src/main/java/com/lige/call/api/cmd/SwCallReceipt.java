package com.lige.call.api.cmd;

public interface SwCallReceipt {
	enum SubReceiptType {
		RECEIPT_TYPE_HTTP,
		RECEIPT_TYPE_CACHE,
		RECEIPT_TYPE_SWITCH,
		RECEIPT_TYPE_CDR,
		RECEIPT_TYPE_SYS
	}		
	
	public static final String RECEIPT_SYS_CALLEND = "end";
	
	public String getId();
	
	public String getName();
	
	public Object getBody();
	
	public SwCallReceiptCallback getCallback();
	
	public SubReceiptType getType();

}
