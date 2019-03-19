package com.lige.call.impl.beans;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.lige.call.impl.api.SwCallCdr;
import com.lige.call.impl.api.SwCallConstant;
import com.lige.call.impl.api.SwCallTimerTask;

class SwCallTaskAssistImpl implements SwCallCdr {
	private static final int DELAY_TIME = 5;
	
	private SwCallTaskImpl task;
	
	private boolean created;
	
	private boolean answered;
	
	// the switch call answered time
	private long call_answered_timestamp;
	
	// the switch call create time
	private long call_created_timestamp;
	
	// the switch call hangup time
	private long call_hangup_timestamp;
	
	// the object create time
	private long initial_timestamp;
	
	private Map<String, SwCallTimerTask> timertasks;
	
	private String uuid;
	
	private int all_duration;
	
	private int hangupCause;
	
	SwCallTaskAssistImpl(SwCallTaskImpl task) {
		this.task = task;
		this.answered = false;
		this.created = false;
		timertasks = new HashMap<String, SwCallTimerTask>();
		SwCallTimeoutCalling callTimeoutTask = new SwCallTimeoutCalling(task);
		SwCallTimeoutRinging callRingTimeoutTask = new SwCallTimeoutRinging(task);
		SwCallTimeoutAll callExistTimeoutTask = new SwCallTimeoutAll(task);
		timertasks.put(SwCallConstant.TIMER_CHECKCALLINGEXPIRED, callTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKANSWEREXPIRED, callRingTimeoutTask);
		timertasks.put(SwCallConstant.TIMER_CHECKFORCEEXIT, callExistTimeoutTask);
		this.initial_timestamp = call_answered_timestamp = Calendar.getInstance().get(Calendar.SECOND);
		this.call_created_timestamp = 0;
		this.call_answered_timestamp = 0;
		this.call_hangup_timestamp = 0;
		this.all_duration = task.getRingDuration() + task.getMaxDuration() + DELAY_TIME;
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
	
	public boolean isForceEnd() {
		return (Calendar.getInstance().get(Calendar.SECOND) - initial_timestamp) > all_duration;
	}

	public Map<String, SwCallTimerTask> getTimertasks() {
		return timertasks;
	}

	public String getUuid() {
		return uuid;
	}
	
	public void callAnswered() {
		answered = true;
		call_answered_timestamp = Calendar.getInstance().get(Calendar.SECOND);
	}
	
	public void callCreated(String uuid) {
		this.uuid = uuid;
		this.created = true;
		call_created_timestamp = Calendar.getInstance().get(Calendar.SECOND);
	}
	
	public void callHangup(int hangupCause) {
		call_hangup_timestamp =  Calendar.getInstance().get(Calendar.SECOND);
		this.hangupCause = hangupCause;
	}

	@Override
	public long getCreateTime() {
		return call_created_timestamp;
	}

	@Override
	public long getAnswerTime() {
		return call_answered_timestamp;
	}

	@Override
	public long getHanupTime() {
		return call_hangup_timestamp;
	}

	@Override
	public int getHangupCause() {
		return hangupCause;
	}

}
