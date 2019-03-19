package com.lige.call.impl.api;

import java.util.Map;

import com.lige.common.call.api.oper.SwCommonCallDialog;

public interface SwCallTask {

	public String getId();
	
	public String getUserId();
	
	public String getRobotId();
	
	public String getCallerNumber();
	
	public String getCalleeNumber();
	
	public String getGateway();
	
	public int getMaxDuration();	
	
	public int getRingDuration();
	
	public SwCommonCallDialog getDialog();
	
	public SwCallOperateHandler getOptHandler(String operName);
	
	public SwCallSwitchEventHandler getMediaEventHandler(String eventName);
	
	public Map<String, SwCallTimerTask> getTimerTasks();
	
	public boolean isExpired();
	
	public void hangup();
	
	public Map<String, SwCallTimerTask> getTimertasks();
	
	public SwCallCdr getCdr();
	
}
