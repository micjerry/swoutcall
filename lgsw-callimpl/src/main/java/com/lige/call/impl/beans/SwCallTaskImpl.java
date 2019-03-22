package com.lige.call.impl.beans;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.api.SwCallConstant;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallOperateHandler;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

class SwCallTaskImpl implements SwCallTask {
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
	
	private Map<String, SwCallTimerTask> timertasks;
	
	private SwitchCallChannelImpl channel;
	
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
		
		timertasks = new HashMap<String, SwCallTimerTask>();
		SwCallTimeoutCalling callTimeoutTask = new SwCallTimeoutCalling(this);
		SwCallTimeoutRinging callRingTimeoutTask = new SwCallTimeoutRinging(this);
		SwCallTimeoutInitial callExistTimeoutTask = new SwCallTimeoutInitial(this);
		timertasks.put(SwCallConstant.TIMER_CHECKCALLINGEXPIRED, callTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKANSWEREXPIRED, callRingTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKFORCEEXIT, callExistTimeoutTask);
		
		channel = new SwitchCallChannelImpl(this);
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
	public SwCallSwitchEventHandler getSwitchEventHandler(String eventName) {
		return eventHandlers.get(eventName);
	}
	
	@Override
	public String toString() {
		return " id = " + id + " callee = " + calleeNumber;
	}
	
	@Override
	public Map<String, SwCallTimerTask> getTimerTasks() {
		return timertasks;
	}
	
	@Override
	public SwitchCallChannel getChannel() {
		return channel;
	}

	@Override
	public String getSwitchHost() {
		return switchHost;
	}

	@Override
	public SwCallDetectNode getCurNode() {
		return channel.getCurNode();
	}

	@Override
	public void goToDialogNode(SwCommonCallDialogNode node) {
		logger.info("Dialog goto node: {}", node.getName());
		channel.goToDialogNode(node);
		
	}

}
