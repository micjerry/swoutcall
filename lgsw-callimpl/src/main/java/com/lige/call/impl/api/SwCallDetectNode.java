package com.lige.call.impl.api;

import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public interface SwCallDetectNode {
	public SwCommonCallDialogNode getNodeDefine();
	
	public int getRetriedTimes();
	
	public void increaseRetriedTimes();
	
	public int getMaxRetryTimes();	
}
