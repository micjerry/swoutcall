package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BaiduSpeechExtJson {
	@JacksonXmlProperty(localName = "snStartTime", isAttribute = true)
    private String snStartTime;

	@JacksonXmlProperty(localName = "snStopTime", isAttribute = true)
	private String snStopTime;

	@JacksonXmlProperty(localName = "speed", isAttribute = true)
	private String speed;
	
	public String getSnStartTime() {
		return snStartTime;
	}

	public void setSnStartTime(String snStartTime) {
		this.snStartTime = snStartTime;
	}

	public String getSnStopTime() {
		return snStopTime;
	}

	public void setSnStopTime(String snStopTime) {
		this.snStopTime = snStopTime;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}
}
