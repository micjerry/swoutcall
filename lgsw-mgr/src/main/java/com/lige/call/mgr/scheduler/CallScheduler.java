package com.lige.call.mgr.scheduler;

import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class CallScheduler {
	private String id;
	
	private Stack<String> numbers;
	
	private SwCommonCallSessionCreatePojo session;
	
	public CallScheduler(String id, Set<String> numbers, SwCommonCallSessionCreatePojo session) {
		this.id = id;
		this.numbers = new Stack<String>();
		this.numbers.addAll(numbers);
		this.session = session;
	}

	public SwCommonCallSessionCreatePojo popTask() {
		if (numbers.isEmpty())
			return null;
		
		String calleeNumber = numbers.pop();
		if (null == calleeNumber || "".equals(calleeNumber))
			return null;
		
		session.setCalleenumber(calleeNumber);
		
		UUID uuid = UUID.randomUUID();
		session.setTaskid(uuid.toString());
		
		return session;
	}

	public String getId() {
		return id;
	}
}
