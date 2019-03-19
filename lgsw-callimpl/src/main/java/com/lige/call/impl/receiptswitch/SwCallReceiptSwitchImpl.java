package com.lige.call.impl.receiptswitch;

import java.util.ArrayList;
import java.util.List;

import com.lige.call.api.cmd.SwCallReceiptCallback;
import com.lige.call.api.cmd.SwCallReceiptSwitch;
import com.lige.common.call.api.esl.SwCommonCallEslCommandPojo;

class SwCallReceiptSwitchImpl extends SwCallReceiptSwitch {
	
	private String id;
	private String name;
	private List<SwCommonCallEslCommandPojo> messages;


	@Override
	public String toString() {
		return "id = " + id + "name = " + name;
	}

	SwCallReceiptSwitchImpl(String id, String name) {
		this.id = id;
		this.name = name;
		messages = new ArrayList<SwCommonCallEslCommandPojo>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getBody() {
		return messages;
	}

	@Override
	public SwCallReceiptCallback getCallback() {
		return null;
	}
	
	public void addMessage(SwCommonCallEslCommandPojo message) {
		messages.add(message);
	}

}
