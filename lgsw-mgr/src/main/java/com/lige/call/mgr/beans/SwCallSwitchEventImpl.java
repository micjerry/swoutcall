package com.lige.call.mgr.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lige.call.api.exe.SwCallSwitchEvent;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwCallSwitchEventImpl implements SwCallSwitchEvent {


    private SwCommonCallEslEventPojo event;
	
	SwCallSwitchEventImpl(SwCommonCallEslEventPojo event) {
		this.event = event;
	}

    @Override
    public String getUid() {
    	return event.getEventHeaders().get(SwCommonCallEslConstant.ESLHEADER_UNIQUE_ID);
    }


	@Override
	public String getEventName() {
		return event.getEventHeaders().get(SwCommonCallEslConstant.ESLHEADER_EVENT_NAME);
	}

	@Override
	public String toString() {
		return event.getEventHeaders().toString();
	}
	

	@Override
	public Map<String, String> getHeaders() {
		return event.getEventHeaders();
	}

	@Override
	public String getId() {
		return event.getEventHeaders().get(SwCommonCallEslConstant.ESLHEADER_EVENT_SEQ);
	}
}
