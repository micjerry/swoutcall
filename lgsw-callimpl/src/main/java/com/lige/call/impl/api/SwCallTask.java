package com.lige.call.impl.api;

import java.util.Map;

import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwCallTask {
	
	enum CallStage {
		CALL_STAGE_INITIAL,
		CALL_STAGE_CREATED,
		CALL_STAGE_ANSWERED,
		CALL_STAGE_HANGUP
	}

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
	
	public SwCallSwitchEventHandler getMediaEventHandler(String eventName);
	
	public Map<String, SwCallTimerTask> getTimerTasks();
	
	public boolean isExpired();
	
	public void hangup();
	
	public Map<String, SwCallTimerTask> getTimertasks();
	
	public SwCallCdr getCdr();
	
	public void setStage(CallStage stage);
	
	public String getUuid();
	
	public void setUuid(String uuid);
	
	public void setHangCause(int cause);
	
	public SwCommonCallDialogNode getCurNode();
	
	public void setCurNode(SwCommonCallDialogNode node);
}
