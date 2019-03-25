package com.lige.call.impl.api;

public enum SwCallState {
	NONE("none"),
	CREATING("creating"),
	RINGING("ringing"),
	CALLING("calling"),
	HANGING("hanging"),
	FINALIZE("finalize");
	
	private final String name;
	
	private SwCallState(String s) {
		name = s;
	}
	
	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}
	
	public String toString() {
		return name;
	}
}
