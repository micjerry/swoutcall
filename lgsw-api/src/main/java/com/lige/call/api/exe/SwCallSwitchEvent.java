package com.lige.call.api.exe;

import java.util.Map;

public interface SwCallSwitchEvent {
	String getId();
	String getEventName();
	Map<String, String> getHeaders();
	String getUid();
}
