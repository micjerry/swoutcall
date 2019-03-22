package com.lige.call.impl.api;

import java.util.Map;

import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwCallTask {

	public String getId();
	
	public String getUserId();
	
	public String getRobotId();
	
	public String getCallerNumber();
	
	public String getCalleeNumber();
	
	public String getSwitchHost();
	
	public String getGateway();
	
	public int getMaxDuration();	
	
	public int getRingDuration();
	
	public SwCommonCallDialog getDialog();
	
	public SwCallOperateHandler getOptHandler(String operName);
	
	public SwCallSwitchEventHandler getSwitchEventHandler(String eventName);
	
	public Map<String, SwCallTimerTask> getTimerTasks();
	
	public SwitchCallChannel getChannel();
	
	public SwCallDetectNode getCurNode();
	
	public void goToDialogNode(SwCommonCallDialogNode node);
	
}
