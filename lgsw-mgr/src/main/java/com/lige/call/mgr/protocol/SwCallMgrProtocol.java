package com.lige.call.mgr.protocol;

public interface SwCallMgrProtocol {
	public static final String SWCALL_CMDTYPE_HEADER = "cmdtype";
	public static final String SWCALL_CALLBACK_HEADER = "callback";
	
	public static final String SWCALL_COMMAND_HTTP = "http";
	public static final String SWCALL_COMMAND_CACHE = "cache";
	public static final String SWCALL_COMMAND_SWITCH = "swcmd";
	public static final String SWCALL_COMMAND_CDR = "cdr";
	public static final String SWCALL_COMMAND_SYS = "sys";
	public static final String SWCALL_COMMAND_BAD = "unknown";
	
	public static final String SWCALL_DEFAULT_CONTENT_TYPE= "application/json";
}
