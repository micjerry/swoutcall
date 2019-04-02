package com.lige.call.impl.api;

import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwitchCallChannel {
	
	public String getUuid();
	
	public int getStateTimeStamp(SwCallState state);
	
	public void setHangupCause(int hangupCause);
	
	public int getHangupCause();
	
	public void setCallState(SwCallState stage, SwCommonCallEslEventPojo pojo);
	
	public SwCallState getCallState();
	
	public void setRecordFile(String recordFileName);
	
	public String getRecordFile();
	
	public void setRecordMsLength(String msLength);
	
	public String getRecordMsLength();
	
	public SwCallPlayAndDetected getPlayAndDetected();
	
	public void setPlayAndDetected(SwCommonCallDialogNode node);
	
	public SwCallDetectNode getLogicNode();
	
	public void gotoLogicNode(SwCommonCallDialogNode node);
}
