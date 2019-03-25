package com.lige.call.impl.api;

public interface SwCallConstant {


	String HTTP_METHOD_POST = "POST";
	
	String TIMER_INITIALIZE_CALL = "initial";
	String TIMER_FINALIZE_CALL = "finalize";
	
	String TIMER_CHKSTAGE_INITIAL = "creating";
	String TIMER_CHKSTAGE_CREATED = "ringing";
	String TIMER_CHKSTAGE_ANSWERED = "calling";
	String TIMER_CHKSTAGE_HANGED = "hanging";
	
	//TODO hangup expired
	String HANGUP_CAUSE_EXPIRED = "501";
	
	public static final int TIMELIMIT_HANGING = 10;
	public static final int TIMELIMIT_CREATING = 20;
	public static final int TIMELIMIT_FINALIZE = 60;
}
