package com.lige.call.impl.beans;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwitchCallChannelImpl implements SwitchCallChannel {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
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
	
	private SwCallState preCallState;
	
	private SwCallState callState;
	
	private int nodeSeq;
	
	private String recordFileName;
	
	private String recordMsLength;
	
	SwitchCallChannelImpl(SwCallTaskImpl task) {
		this.task = task;
		this.call_initial_timestamp = 0;
		this.call_created_timestamp = 0;
		this.call_answered_timestamp = 0;
		this.call_hangup_timestamp = 0;
		this.callState = SwCallState.NONE;
		this.preCallState = SwCallState.NONE;
		this.nodeSeq = 0;
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
		this.curNode = new SwCallDetectNodeImpl(curNode, this.nodeSeq++);
	}

	@Override
	public void setCallState(SwCallState state, SwCommonCallEslEventPojo event) {
		logger.info("call {} state change from {} to {}", task.getId(), this.callState, state);
		this.preCallState = this.callState;
		this.callState = state;
		switch (state) {
		case CREATING:
			this.call_initial_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case RINGING:
			this.uuid = SwCommonCallEslEventParser.getSwitchChannelId(event);
			call_created_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case CALLING:
			call_answered_timestamp = Calendar.getInstance().get(Calendar.SECOND);
			break;
		case HANGING:
			call_hangup_timestamp =  Calendar.getInstance().get(Calendar.SECOND);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public SwCallState getCallState() {
		return this.callState;
	}

	public SwCallState getPreCallState() {
		return preCallState;
	}


	@Override
	public void setRecordFile(String recordFileName) {
		this.recordFileName = recordFileName;
		
	}

	@Override
	public String getRecordFile() {
		return recordFileName;
	}


	@Override
	public void setRecordMsLength(String msLength) {
		this.recordMsLength = msLength;
		
	}

	@Override
	public String getRecordMsLength() {
		return recordMsLength;
	}
}
