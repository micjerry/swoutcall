package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BaiduSpeechInterpretation {
	@JacksonXmlProperty(localName = "grammar", isAttribute = true)
    private String grammar;

	@JacksonXmlProperty(localName = "confidence", isAttribute = true)
    private String confidence;
	
	@JacksonXmlElementWrapper(localName = "instance", useWrapping = false)
	private BaiduSpeechInstance instance;

	@JacksonXmlProperty(localName = "input", isAttribute = true)
    private String input;
	
	public String getGrammar() {
		return grammar;
	}

	public void setGrammar(String grammar) {
		this.grammar = grammar;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
	
	public BaiduSpeechInstance getInstance() {
		return instance;
	}

	public void setInstance(BaiduSpeechInstance instance) {
		this.instance = instance;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
