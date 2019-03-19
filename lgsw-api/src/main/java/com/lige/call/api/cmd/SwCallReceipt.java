package com.lige.call.api.cmd;

public interface SwCallReceipt {
	enum SubCommandType {
		COMMAND_TYPE_HTTP,
		COMMAND_TYPE_CACHE,
		COMMAND_TYPE_SWITCH,
		COMMAND_TYPE_CDR,
		COMMAND_TYPE_SYS
	}		
	
	public String getId();
	
	public String getName();
	
	public Object getBody();
	
	public SwCallReceiptCallback getCallback();
	
	public SubCommandType getType();

}
