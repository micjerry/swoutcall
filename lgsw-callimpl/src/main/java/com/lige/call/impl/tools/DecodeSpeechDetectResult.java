package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "result")public final class DecodeSpeechDetectResult {
	@JacksonXmlElementWrapper(localName = "interpretation", useWrapping = false)
	private DecodeResultInterpretation interpretation;
	
	public DecodeResultInterpretation getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(DecodeResultInterpretation interpretation) {
		this.interpretation = interpretation;
	}	
}
