package com.lige.call.impl.api;

import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

public interface SwitchCallChannel {
	enum CallStage {
		CALL_STAGE_INITIAL,
		CALL_STAGE_CREATED,
		CALL_STAGE_ANSWERED,
		CALL_STAGE_HANGUP
	}
	
	public String getUuid();
	
	public long getCreateTime();
	
	public long getAnswerTime();
	
	public long getHanupTime();
	
	public int getHangupCause();
	
	public void setCallStage(CallStage stage, SwCommonCallEslEventPojo pojo);
	
	public boolean testStage(CallStage stage);
	
	public boolean isRingExpired();
	
	public boolean isCallingExpired();

}
