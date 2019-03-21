package com.lige.call.impl.beans;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.lige.call.impl.api.SwCallCdr;
import com.lige.call.impl.api.SwCallConstant;
import com.lige.call.impl.api.SwCallTimerTask;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwCallTaskAssistImpl implements SwCallCdr {
	
	private SwCallTaskImpl task;
	
	private boolean cmdsend;
	
	private boolean created;
	
	private boolean answered;
	
	// the call initial time
	private long call_initial_timestamp;
	
	// the switch call switch channel create time
	private long call_created_timestamp;
	
	// the switch call answered time
	private long call_answered_timestamp;
	
	// the switch call hang time
	private long call_hangup_timestamp;
	
	private Map<String, SwCallTimerTask> timertasks;
	
	private String uuid;
	
	private int hangupCause;
	
	private SwCommonCallDialogNode curNode;
	
	SwCallTaskAssistImpl(SwCallTaskImpl task) {
		this.task = task;
	}
	
	public void initialize() {
		this.setCmdsend(false);
		this.answered = false;
		this.created = false;
		this.call_initial_timestamp = 0;
		this.call_created_timestamp = 0;
		this.call_answered_timestamp = 0;
		this.call_hangup_timestamp = 0;
		
		timertasks = new HashMap<String, SwCallTimerTask>();
		SwCallTimeoutCalling callTimeoutTask = new SwCallTimeoutCalling(task);
		SwCallTimeoutRinging callRingTimeoutTask = new SwCallTimeoutRinging(task);
		SwCallTimeoutInitial callExistTimeoutTask = new SwCallTimeoutInitial(task);
		timertasks.put(SwCallConstant.TIMER_CHECKCALLINGEXPIRED, callTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKANSWEREXPIRED, callRingTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKFORCEEXIT, callExistTimeoutTask);
	}
	
	public boolean isAnswered() {
		return answered;
	}
	
	public boolean isExpired() {
		if (!created || !answered)
			return false;
		return (Calendar.getInstance().get(Calendar.SECOND) - call_answered_timestamp) > task.getMaxDuration();
	}	
	
	public boolean isRingExpired() {
		if (answered)
			return false;
		return (Calendar.getInstance().get(Calendar.SECOND) - call_created_timestamp) > task.getRingDuration();
	}

	public Map<String, SwCallTimerTask> getTimertasks() {
		return timertasks;
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void callInitialed() {
		this.cmdsend = true;
		this.call_initial_timestamp = Calendar.getInstance().get(Calendar.SECOND);
	}
	
	public void callCreated() {
		this.created = true;
		call_created_timestamp = Calendar.getInstance().get(Calendar.SECOND);
	}
	
	public void callAnswered() {
		answered = true;
		call_answered_timestamp = Calendar.getInstance().get(Calendar.SECOND);
	}

	
	public void callHangup() {
		call_hangup_timestamp =  Calendar.getInstance().get(Calendar.SECOND);
	}

	@Override
	public long getCreateTime() {
		return call_created_timestamp;
	}

	@Override
	public long getAnswerTime() {
		return call_answered_timestamp;
	}

	public void setHangupCause(int hangupCause) {
		this.hangupCause = hangupCause;
	}

	@Override
	public long getHanupTime() {
		return call_hangup_timestamp;
	}

	@Override
	public int getHangupCause() {
		return hangupCause;
	}

	public boolean isCmdsend() {
		return cmdsend;
	}

	public void setCmdsend(boolean cmdsend) {
		this.cmdsend = cmdsend;
	}

	public long getInitialTime() {
		return call_initial_timestamp;
	}

	public SwCommonCallDialogNode getCurNode() {
		return curNode;
	}

	public void setCurNode(SwCommonCallDialogNode curNode) {
		this.curNode = curNode;
	}

}
