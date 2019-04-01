package com.lige.call.impl.receiptcdr;

import java.util.Map;

import com.lige.call.api.cmd.SwCallReceiptCdr;
import com.lige.call.impl.api.SwCallPlayAndDetected;
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
		
		addCommonParameters(pojo, task);
		addCallParameters(pojo, task);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallDialogPlayCdr(SwCallTask task) {
		if (null == task || task.getChannel().getPlayAndDetected() == null)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		
		addCommonParameters(pojo, task);
		addDialogPlayParameters(pojo, task);
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallDialogDetectCdr(SwCallTask task) {
		if (null == task || task.getChannel().getPlayAndDetected() == null)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		
		addCommonParameters(pojo, task);
		addDialogDetectParameters(pojo, task);
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallRecordCdr(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		SwCommonCallCdrPojo pojo = new SwCommonCallCdrPojo();
		pojo.setId(task.getId());
		pojo.setName(SwCommonCallCdrConstant.CDRNAME_CALL_RECORD);
		addCommonParameters(pojo, task);
		addRecordParameters(pojo, task);
		
		return cdrImpl;
	}
	
	private static void addCommonParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_USER, task.getUserId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_ROBOT, task.getRobotId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_GATEWAY, task.getGateway());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_CALLER, task.getCallerNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_CALLEE, task.getCalleeNumber());
	}
	
	private static void addCallParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CREATETIME, Long.toString(task.getChannel().getCreateTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ANSWERTIME, Long.toString(task.getChannel().getAnswerTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPTIME, Long.toString(task.getChannel().getHanupTime()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPCAUSE, Integer.toString(task.getChannel().getHangupCause()));
	}
	
	private static void addDialogPlayParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		SwCallPlayAndDetected play = task.getChannel().getPlayAndDetected();
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_SEQ, Integer.toString(play.getPlaySeq()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_FILEID, play.getFileId());
	}
	
	private static void addDialogDetectParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		SwCallPlayAndDetected play = task.getChannel().getPlayAndDetected();
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_SEQ, Integer.toString(play.getDetectedSeq()));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_DETECT, play.getDetected());
	}
	
	private static void addRecordParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_DIALOG_FILEID, task.getChannel().getRecordFile());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_RECORD_MSLENGTH, task.getChannel().getRecordMsLength());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_RECORD_HOST, task.getSwitchHost());
	}
	

}
