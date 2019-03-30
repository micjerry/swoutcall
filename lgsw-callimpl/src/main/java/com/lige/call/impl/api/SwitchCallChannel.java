package com.lige.call.impl.api;

import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwitchCallChannel {
	
	public String getUuid();
	
	public long getCreateTime();
	
	public long getAnswerTime();
	
	public long getHanupTime();
	
	public int getHangupCause();
	
	public void setCallState(SwCallState stage, SwCommonCallEslEventPojo pojo);
	
	public SwCallState getCallState();
	
	public void setRecordFile(String recordFileName);
	
	public String getRecordFile();
	
	public void setRecordMsLength(String msLength);
	
	public String getRecordMsLength();
	
	public SwCommonCallDialogNode getCurPlayedNode();
	
	public void setCurPlayedNode(SwCommonCallDialogNode node);
}
