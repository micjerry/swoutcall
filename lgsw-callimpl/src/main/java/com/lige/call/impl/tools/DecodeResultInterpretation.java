package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public final class DecodeResultInterpretation {
	@JacksonXmlProperty(localName = "confidence", isAttribute = true)
    private int confidence;
	
	@JacksonXmlProperty(localName = "instance")
	private String instance;
	
	@JacksonXmlProperty(localName = "input")
	private String input;
	
	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
