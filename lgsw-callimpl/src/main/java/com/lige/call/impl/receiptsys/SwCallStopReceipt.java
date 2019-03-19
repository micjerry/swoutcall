package com.lige.call.impl.receiptsys;

import com.lige.call.api.cmd.SwCallReceiptCallback;
import com.lige.call.api.cmd.SwCallReceiptSys;

class SwCallStopReceipt extends SwCallReceiptSys {

	private  String id = "";
	private  String name = "";

	private Object body = null;

	public  SwCallStopReceipt(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		
		return this.id;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public Object getBody() {
		
		return body;
	}

	public void setBody(Object obj) {

		this.body= obj;
	}

	@Override
	public SwCallReceiptCallback getCallback() {
		
		return null;
	}

	@Override
	public String toString() {
		return "id = " + id + " name = " + name;
	}
	
}
