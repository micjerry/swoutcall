package com.lige.call.api.cmd;


public abstract class SwCallReceiptHttp implements SwCallReceipt {	
	public final SubCommandType getType() {
		return SubCommandType.COMMAND_TYPE_HTTP;
	}
	
	public abstract String getUrl();
	
	public abstract String getMethod();
	
	public abstract String getHost();
}
