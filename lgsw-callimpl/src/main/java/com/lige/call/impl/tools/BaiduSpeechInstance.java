package com.lige.call.impl.tools;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BaiduSpeechInstance {
	@JacksonXmlProperty(localName = "callId", isAttribute = true)
    private String callId;

	@JacksonXmlProperty(localName = "logId", isAttribute = true)
    private String logId;
	
	@JacksonXmlProperty(localName = "rolecategory", isAttribute = true)
    private String rolecategory;
	
	@JacksonXmlElementWrapper(localName = "extJson", useWrapping = false)
	private BaiduSpeechExtJson extJson;
	
	@JacksonXmlProperty(localName = "categotyId", isAttribute = true)
    private String categotyId;
	
	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getRolecategory() {
		return rolecategory;
	}

	public void setRolecategory(String rolecategory) {
		this.rolecategory = rolecategory;
	}

	public BaiduSpeechExtJson getExtJson() {
		return extJson;
	}

	public void setExtJson(BaiduSpeechExtJson extJson) {
		this.extJson = extJson;
	}

	public String getCategotyId() {
		return categotyId;
	}

	public void setCategotyId(String categotyId) {
		this.categotyId = categotyId;
	}
}
