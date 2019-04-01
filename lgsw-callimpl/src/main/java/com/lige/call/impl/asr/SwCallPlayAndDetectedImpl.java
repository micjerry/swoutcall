package com.lige.call.impl.asr;

import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwCallPlayAndDetectedImpl implements SwCallPlayAndDetected {
	
	private String fileName;
	
	private String fileId;
	
	private int duration;
	
	private String detected;
	
	private boolean playStarted;
	
	private boolean playFinished;
	
	private boolean hangAfterPlay;
	
	private int playSeq;
	
	private int detectSeq;
	
	public SwCallPlayAndDetectedImpl(SwCommonCallDialogNode node, int seq) {
		this.fileName = node.getPlay();
		this.fileId = node.getFileId();
		this.duration = node.getDuration();
		this.detected = null;
		this.playStarted = false;
		this.playFinished = false;
		this.hangAfterPlay = node.isHangup();
		this.playSeq = seq;
		this.detectSeq = seq + 1;
	}

	@Override
	public String getFileId() {
		return fileId;
	}

	@Override
	public void setDetected(String detected) {
		this.detected = detected;		
	}

	@Override
	public String getDetected() {
		return detected;
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
		return playFinished;
	}

	@Override
	public void setPlayFinished(boolean finish) {
		this.playFinished = finish;
	}

	@Override
	public boolean isHangupAfterPlay() {
		return hangAfterPlay;
	}

	@Override
	public void setHangupAfterPlay(boolean hangup) {
		this.hangAfterPlay = hangup;
	}

	@Override
	public int getPlaySeq() {
		return playSeq;
	}

	@Override
	public int getDetectedSeq() {
		return detectSeq;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public int getDuration() {
		return duration;
	}

}
