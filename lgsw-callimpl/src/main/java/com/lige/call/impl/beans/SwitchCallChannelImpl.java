package com.lige.call.impl.beans;

import java.util.Calendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.asr.AsrFactory;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;

class SwitchCallChannelImpl implements SwitchCallChannel {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallTaskImpl task;
	
	private String uuid;
	
	private int hangupCause;
	
	private SwCallDetectNode curNode;
	
	private SwCallState callState;
	
	private String recordFileName;
	
	private String recordMsLength;
	
	private SwCallDetectNode logicNode;
	
	private SwCallPlayAndDetected playAndDetected;
	
	private HashMap<String, Integer> timeStamps;
	
	SwitchCallChannelImpl(SwCallTaskImpl task) {
		this.task = task;
		this.callState = SwCallState.NONE;
		timeStamps = new HashMap<String, Integer>();
	}
	

	@Override
	public String getUuid() {
		return uuid;
	}
	
	@Override
	public int getStateTimeStamp(SwCallState state) {
		Integer value = timeStamps.get(state.toString());
		if (null == value) {
			return 0;
		}
		
		return value;
	}

	@Override
	public int getHangupCause() {
		return hangupCause;
	}

	public SwCallDetectNode getCurNode() {
		return curNode;
	}

	@Override
	public void setCallState(SwCallState state, SwCommonCallEslEventPojo event) {
		logger.info("task: {} state change from {} to {}", task.getId(), this.callState, state);
		this.callState = state;
		timeStamps.put(state.toString(), Calendar.getInstance().get(Calendar.SECOND));
		switch (state) {
		case CREATING:
		case CALLING:
		case HANGING:
			break;
		case RINGING:
			this.uuid = SwCommonCallEslEventParser.getSwitchChannelId(event);
			break;
		default:
			break;
		}
	}
	
	@Override
	public SwCallState getCallState() {
		return this.callState;
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


	@Override
	public SwCallPlayAndDetected getPlayAndDetected() {
		return playAndDetected;
	}


	@Override
	public void setPlayAndDetected(SwCommonCallDialogNode node) {
		int seq = 0;
		if (null != this.playAndDetected)
			seq = this.playAndDetected.getDetectedSeq() + 1;
		this.playAndDetected = AsrFactory.makePlayAndDetected(node, seq);
		if (node.getSysType().equals(SwCommonCallOperConstant.DIALOG_SYSTYPE_RETRY)) {
			if (null != logicNode) {
				logicNode.increaseRetriedTimes();
			}
		}
	}


	@Override
	public SwCallDetectNode getLogicNode() {
		return logicNode;
	}


	@Override
	public void gotoLogicNode(SwCommonCallDialogNode node) {
		logger.info("task: {} goto node {}", task.getId(), node.getName());
		this.logicNode = AsrFactory.makeLogicNode(node);
		this.setPlayAndDetected(node);
	}


	@Override
	public void setHangupCause(int hangupCause) {
		this.hangupCause = hangupCause;
	}

}
