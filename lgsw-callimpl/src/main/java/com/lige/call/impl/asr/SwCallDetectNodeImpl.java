package com.lige.call.impl.asr;

import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwCallDetectNodeImpl implements SwCallDetectNode {
	
	private SwCommonCallDialogNode node;
	
	private int retriedTimes = 0;
	
	public SwCallDetectNodeImpl(SwCommonCallDialogNode node) {
		this.node = node;
	}

	@Override
	public SwCommonCallDialogNode getNodeDefine() {
		return node;
	}

	@Override
	public int getRetriedTimes() {
		return retriedTimes;
	}

	@Override
	public void increaseRetriedTimes() {
		this.retriedTimes++;
		
	}

	@Override
	public int getMaxRetryTimes() {
		return node.getRetryTimes();
	}

}
