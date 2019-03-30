package com.lige.call.impl.beans;

import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwCallDetectNodeImpl implements SwCallDetectNode{	
	private SwCommonCallDialogNode node;
	
	private boolean playStarted;
	
	private boolean playStoped;
	
	private String detected;
	
	private int retry;
	
	public SwCallDetectNodeImpl(SwCommonCallDialogNode node) {
		this.node = node;
		this.playStarted = false;
		this.playStoped = false;
		this.retry = 0;
	}

	@Override
	public SwCommonCallDialogNode getNodeDefine() {
		return node;
	}

	@Override
	public boolean isPlayStarted() {
		return playStarted;
	}

	@Override
	public void setPlayStarted(boolean start) {
		this.playStarted = start;	
	}

	@Override
	public boolean isPlayFinished() {
		return playStoped;
	}

	@Override
	public void setPlayFinished(boolean finish) {
		this.playStoped = finish;
		
	}

	@Override
	public String getDetected() {
		return detected;
	}

	@Override
	public void setDetected(String detected) {
		this.detected = detected;	
	}

	@Override
	public int getRetry() {
		return retry;
	}

	@Override
	public void increaseRetry() {
		this.retry++;
		
	}
}
