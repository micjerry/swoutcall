package com.lige.call.impl.api;

public interface SwCallPlayAndDetected {
	public String getFileName();
	
	public String getFileId();
	
	public void setDetected(String detected);
	
	public String getDetected();
	
	public boolean isPlayStarted();
	
	public void setPlayStarted(boolean start);
	
	public boolean isPlayFinished();
	
	public void setPlayFinished(boolean finish);
	
	public boolean isHangupAfterPlay();
	
	public void setHangupAfterPlay(boolean hangup);
	
	public int getPlaySeq();
	
	public int getDetectedSeq();
	
	public int getDuration();
	
}
