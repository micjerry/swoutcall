package com.lige.call.impl.beans;

import java.util.Calendar;

import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwitchCallChannelImpl implements SwitchCallChannel {
	
	private SwCallTaskImpl task;
	
	private boolean initialed;
	
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
	
	private String uuid;
	
	private int hangupCause;
	
	private SwCallDetectNode curNode;
	
	SwitchCallChannelImpl(SwCallTaskImpl task) {
		this.task = task;
		this.initialed = false;
		this.answered = false;
		this.created = false;
		this.call_initial_timestamp = 0;
		this.call_created_timestamp = 0;
		this.call_answered_timestamp = 0;
		this.call_hangup_timestamp = 0;
	}
	
	@Override
	public boolean isCallingExpired() {
		if (!created || !answered)
			return false;
		return (Calendar.getInstance().get(Calendar.SECOND) - call_answered_timestamp) > task.getMaxDuration();
	}	
	
	@Override
	public boolean isRingExpired() {
		if (answered)
			return false;
		return (Calendar.getInstance().get(Calendar.SECOND) - call_created_timestamp) > task.getRingDuration();
	}

	@Override
	public String getUuid() {
		return uuid;
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

	public long getInitialTime() {
		return call_initial_timestamp;
	}

	public SwCallDetectNode getCurNode() {
		return curNode;
	}

	public void goToDialogNode(SwCommonCallDialogNode curNode) {
		this.curNode = new SwCallDetectNodeImpl(curNode);
	}

	@Override
	public void setCallStage(CallStage stage, SwCommonCallEslEventPojo event) {
		switch (stage) {
		case CALL_STAGE_INITIAL:
			this.initialed = true;
			this.call_initial_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case CALL_STAGE_CREATED:
			this.created = true;
			this.uuid = SwCommonCallEslEventParser.getSwitchChannelId(event);
			call_created_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case CALL_STAGE_ANSWERED:
			this.answered = true;
			call_answered_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case CALL_STAGE_HANGUP:
			call_hangup_timestamp =  Calendar.getInstance().get(Calendar.SECOND);
			break;
		default:
			break;
		}
		
	}

	@Override
	public boolean testStage(CallStage stage) {
		boolean result = false;
		switch (stage) {
		case CALL_STAGE_INITIAL:
			result = this.initialed;
			break;
		case CALL_STAGE_CREATED:
			result = this.created;
			break;
		case CALL_STAGE_ANSWERED:
			result = this.answered;
			break;
		default:
			break;	
		}
		
		return result;
	}

}
