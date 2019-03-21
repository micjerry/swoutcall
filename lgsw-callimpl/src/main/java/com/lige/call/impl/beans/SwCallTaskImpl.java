package com.lige.call.impl.beans;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.impl.api.SwCallCdr;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class SwCallTaskImpl implements SwCallTask {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String id;
	
	private String userId;
	
	private String robotId;
	
	private String callerNumber;
	
	private String calleeNumber;
	
	private String switchHost;
	
	private String gateWay;
	
	private int maxDuration;
	
	private int ringDuration;
	
	private SwCommonCallDialog dialog;
	
	private HashMap<String, SwCallSwitchEventHandler> eventHandlers;
	
	private HashMap<String, SwCallOperateHandler> operateHandlers;
	
	private SwCallTaskAssistImpl assistImpl;
	
	public SwCallTaskImpl(SwCommonCallSessionCreatePojo req, HashMap<String, SwCallOperateHandler> operateHandlers, 
			HashMap<String, SwCallSwitchEventHandler> eventHandlers) {
		this.id = req.getTaskid();
		this.userId = req.getUserid();
		this.robotId = req.getRobotid();
		this.callerNumber = req.getCallernumber();
		this.calleeNumber = req.getCalleenumber();
		this.switchHost = req.getSwid();
		this.gateWay = req.getGateway();
		this.maxDuration = req.getMaxduration();
		this.dialog = req.getDialog();
		this.ringDuration = req.getRingduration();
		
		this.operateHandlers = operateHandlers;
		this.eventHandlers = eventHandlers;
		
		assistImpl = new SwCallTaskAssistImpl(this);
		assistImpl.initialize();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getRobotId() {
		return robotId;
	}

	@Override
	public String getCallerNumber() {
		return callerNumber;
	}

	@Override
	public String getCalleeNumber() {
		return calleeNumber;
	}

	@Override
	public String getGateway() {
		return gateWay;
	}

	@Override
	public int getMaxDuration() {
		return maxDuration;
	}
	
	@Override
	public int getRingDuration(){
		return ringDuration;
	}

	@Override
	public SwCommonCallDialog getDialog() {
		return dialog;
	}

	@Override
	public SwCallOperateHandler getOptHandler(String operName) {
		return operateHandlers.get(operName);
	}

	@Override
	public SwCallSwitchEventHandler getMediaEventHandler(String eventName) {
		return eventHandlers.get(eventName);
	}
	
	@Override
	public Map<String, SwCallTimerTask> getTimerTasks() {
		return assistImpl.getTimertasks();
	}
	
	@Override
	public boolean isExpired() {
		return assistImpl.isExpired();
	}
	
	@Override
	public void hangup() {
		//TODO
		return;
	}
	
	@Override
	public String toString() {
		return " id = " + id + " callee = " + calleeNumber;
	}
	
	@Override
	public Map<String, SwCallTimerTask> getTimertasks() {
		return assistImpl.getTimertasks();
	}
	
	public SwCallTaskAssistImpl getAssistImpl() {
		return assistImpl;
	}
	
	@Override
	public SwCallCdr getCdr() {
		return assistImpl;
	}

	@Override
	public String getSwitchHost() {
		return switchHost;
	}
	
	@Override
	public void setStage(CallStage stage) {
		switch (stage) {
		case CALL_STAGE_INITIAL:
			assistImpl.callInitialed();
			break;
		case CALL_STAGE_CREATED:
			assistImpl.callCreated();
			break;
		case CALL_STAGE_ANSWERED:
			assistImpl.callAnswered();
			break;
		case CALL_STAGE_HANGUP:
			assistImpl.callHangup();
			break;
		default:
			break;
		}
	}

	@Override
	public void setUuid(String uid) {
		assistImpl.setUuid(uid);	
	}

	@Override
	public void setHangCause(int cause) {
		assistImpl.setHangupCause(cause);
	}

	@Override
	public String getUuid() {
		return assistImpl.getUuid();
	}

	@Override
	public SwCommonCallDialogNode getCurNode() {
		return assistImpl.getCurNode();
	}

	@Override
	public void setCurNode(SwCommonCallDialogNode node) {
		logger.info("Dialog goto node: {}", node.getName());
		assistImpl.setCurNode(node);
		
	}

}
