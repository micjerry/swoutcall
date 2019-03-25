package com.lige.call.impl.receiptcdr;

import java.util.Map;

import com.lige.call.api.cmd.SwCallReceiptCdr;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.cdr.SwCommonCallCdrConstant;
import com.lige.common.call.api.cdr.SwCommonCallCdrPojo;

public class SwCallCdrReceiptFactory {
	
	public static SwCallReceiptCdr makeCallEventCdr(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		cdrImpl.setCdr(pojo);
		
		addParameters(pojo, task);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallDialogCdr(SwCallTask task) {
		if (null == task || task.getCurNode() == null)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		
		addDialogParameters(pojo, task);
		return cdrImpl;
	}
	
	private static void addParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_USER, task.getUserId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ROBOT, task.getRobotId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_GATEWAY, task.getGateway());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CALLER, task.getCallerNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CALLEE, task.getCalleeNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CREATETIME, Long.toString(task.getChannel().getCreateTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ANSWERTIME, Long.toString(task.getChannel().getAnswerTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPTIME, Long.toString(task.getChannel().getHanupTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPCAUSE, Integer.toString(task.getChannel().getHangupCause()));
	}
	
	private static void addDialogParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		SwCallDetectNode curNode = task.getCurNode();
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_SEQ, Integer.toString(curNode.getSeq()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_FILEID, curNode.getNodeDefine().getFileId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_DETECT, curNode.getDetected());
	}
	

}
