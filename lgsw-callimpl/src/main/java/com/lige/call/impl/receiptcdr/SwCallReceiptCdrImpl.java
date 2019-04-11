package com.lige.call.impl.receiptcdr;

import java.util.HashMap;

import com.lige.call.api.cmd.SwCallReceiptCallback;
import com.lige.call.api.cmd.SwCallReceiptCdr;
import com.lige.common.call.api.cdr.SwCommonCallCdrPojo;

class SwCallReceiptCdrImpl extends SwCallReceiptCdr {
	
	private String id;
	
	private String name;
	
	private SwCommonCallCdrPojo cdr;
	
	public SwCallReceiptCdrImpl(String id, String name) {
		this.id = id;
		this.name = name;
		cdr = new SwCommonCallCdrPojo();
		
		cdr.setId(id);
		cdr.setName(name);
		cdr.setParameters(new HashMap<String, String>());
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
		return cdr;
	}

	@Override
	public SwCallReceiptCallback getCallback() {
		return null;
	}

	public SwCommonCallCdrPojo getCdr() {
		return cdr;
	}
	
	

}
