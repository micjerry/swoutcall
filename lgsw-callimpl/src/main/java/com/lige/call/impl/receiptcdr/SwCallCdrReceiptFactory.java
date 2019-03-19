package com.lige.call.impl.receiptcdr;

import java.util.Map;

import com.lige.call.api.cmd.SwCallReceiptCdr;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.cdr.SwCommonCallCdrConstant;
import com.lige.common.call.api.cdr.SwCommonCallCdrPojo;

public class SwCallCdrReceiptFactory {
	
	private static final String RECEIPT_NAME_CDR_CREATE = "createcall";
	private static final String RECEIPT_NAME_CDR_ANSWER = "answercall";
	private static final String RECEIPT_NAME_CDR_DESTROY = "endcall";
	
	public static SwCallReceiptCdr makeCallCreateEvent(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), RECEIPT_NAME_CDR_CREATE);
		
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_CREATE);
		cdrImpl.setCdr(pojo);
		
		addParameters(pojo, task);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallAnswerEvent(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), RECEIPT_NAME_CDR_ANSWER);
		
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_ANSWER);
		addParameters(pojo, task);
		cdrImpl.setCdr(pojo);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallHangupEvent(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), RECEIPT_NAME_CDR_DESTROY);
		
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_DESTROY);
		addParameters(pojo, task);
		cdrImpl.setCdr(pojo);
		
		return cdrImpl;
	}
	
	private static void addParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_USER, task.getUserId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ROBOT, task.getRobotId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_GATEWAY, task.getGateway());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CALLER, task.getCallerNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CALLEE, task.getCalleeNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CREATETIME, Long.toString(task.getCdr().getCreateTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ANSWERTIME, Long.toString(task.getCdr().getAnswerTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPTIME, Long.toString(task.getCdr().getHanupTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPCAUSE, Integer.toString(task.getCdr().getHangupCause()));
	}

}
