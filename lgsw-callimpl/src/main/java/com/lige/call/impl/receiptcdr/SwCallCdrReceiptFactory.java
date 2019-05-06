package com.lige.call.impl.receiptcdr;

import java.util.Map;

import com.lige.call.api.cmd.SwCallReceiptCdr;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.cdr.SwCommonCallCdrConstant;
import com.lige.common.call.api.cdr.SwCommonCallCdrPojo;

public class SwCallCdrReceiptFactory {
	
	public static SwCallReceiptCdr makeCallEventCdr(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = makeCdrTemplate(task, SwCommonCallCdrConstant.CDRNAME_CALL_EVENT);
		
		addCallParameters(cdrImpl.getCdr(), task);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallDialogPlayCdr(SwCallTask task) {
		if (null == task || task.getChannel().getPlayAndDetected() == null)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = makeCdrTemplate(task, SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		
		addDialogPlayParameters(cdrImpl.getCdr(), task);

		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallDialogDetectCdr(SwCallTask task) {
		if (null == task || task.getChannel().getPlayAndDetected() == null)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = makeCdrTemplate(task, SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);
		
		addDialogDetectParameters(cdrImpl.getCdr(), task);
		
		return cdrImpl;
	}
	
	public static SwCallReceiptCdr makeCallRecordCdr(SwCallTask task) {
		if (null == task)
			return null;
		
		SwCallReceiptCdrImpl cdrImpl = makeCdrTemplate(task, SwCommonCallCdrConstant.CDRNAME_CALL_DIALOG);

		addRecordParameters(cdrImpl.getCdr(), task);
		
		return cdrImpl;
	}
	
	private static SwCallReceiptCdrImpl makeCdrTemplate(SwCallTask task, String name) {
		SwCallReceiptCdrImpl cdrImpl = new SwCallReceiptCdrImpl(task.getId(), name);
		addCommonParameters(cdrImpl.getCdr(), task);
		
		return cdrImpl;
	}
	
	private static void addCommonParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_USER, task.getUserId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_ROBOT, task.getRobotId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_GATEWAY, task.getGateway());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_CALLER, task.getCallerNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_CALLEE, task.getCalleeNumber());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_GROUP, task.getGroupId());
		paras.put(SwCommonCallCdrConstant.CDRFIELD_ALL_DIALOG, task.getDialogId());
	}
	
	private static void addCallParameters(SwCommonCallCdrPojo pojo, SwCallTask task) {
		Map<String,String> paras = pojo.getParameters();
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_CREATETIME, Integer.toString(task.getChannel().getStateTimeStamp(SwCallState.CREATING)));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_ANSWERTIME, Integer.toString(task.getChannel().getStateTimeStamp(SwCallState.CALLING)));
		paras.put(SwCommonCallCdrConstant.CDRFIELD_CALL_HANGUPTIME, Integer.toString(task.getChannel().getStateTimeStamp(SwCallState.HANGING)));
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
