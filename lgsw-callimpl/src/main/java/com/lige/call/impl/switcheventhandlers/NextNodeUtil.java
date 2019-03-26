package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.receiptcdr.SwCallCdrReceiptFactory;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.call.impl.tools.SwCallSceneLogic;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class NextNodeUtil {
	private static final Logger logger = LoggerFactory.getLogger(NextNodeUtil.class);
	
	public static List<SwCallReceipt> nextStep(SwCallTask task) {
		if (null == task.getCurNode().getDetected() || !task.getCurNode().isPlayFinished()) {
			logger.info("call: {} wait detect result or play finished", task.getId());
			return null;
		}
		
		SwCommonCallDialogNode nextNode = SwCallSceneLogic.getNextNode(task.getCurNode().getNodeDefine(), task.getCurNode().getDetected(), task.getDialog());
		
		if (null == nextNode) {
			logger.error("call: {} can not find next node hangup it, cur node: {}", task.getId(), task.getCurNode().getNodeDefine().getName());
			return  ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
		}
		
		List<SwCallReceipt> results = ReceiptLoader.loadReceipt(SwCallCdrReceiptFactory.makeCallDialogCdr(task));
		task.goToDialogNode(nextNode);
		results.add(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task.getChannel().getUuid(), nextNode));
		return results;
	}
	
	public static String getNodeName(SwCallTask task) {
		SwCallDetectNode node = task.getCurNode();
		if (null == node)
			return null;
		
		SwCommonCallDialogNode nodeDefine = node.getNodeDefine();
		
		if (null == nodeDefine) 
			return null;

		return nodeDefine.getName();
	}
	
	public static void refreshChannel(SwCallState state, SwCallTask task, SwCommonCallEslEventPojo event) {
		SwitchCallChannel channel = task.getChannel();
		channel.setCallState(state, event);
	}
}
