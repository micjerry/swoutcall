package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "result") public class BaiduSpeechResult {
	@JacksonXmlElementWrapper(localName = "interpretation", useWrapping = false)
	private BaiduSpeechInterpretation interpretation;

	public BaiduSpeechInterpretation getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(BaiduSpeechInterpretation interpretation) {
		this.interpretation = interpretation;
	}
}
