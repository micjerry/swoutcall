package com.lige.call.impl.api;

import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwCallDetectNode {
	public SwCommonCallDialogNode getNodeDefine();
	
	public boolean isPlayStarted();
	
	public void setPlayStarted(boolean start);
	
	public boolean isPlayFinished();
	
	public void setPlayFinished(boolean finish);
	
	public String getDetected();
	
	public void setDetected(String detected);
	
	public int getRetry();
	
	public void increaseRetry();
}
