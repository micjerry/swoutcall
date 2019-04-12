package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwitchCallChannel;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.SwCallSceneLogic;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;

class NextNodeUtil {
	private static final Logger logger = LoggerFactory.getLogger(NextNodeUtil.class);
	
	public static List<SwCallReceipt> nextStep(SwCallTask task, List<SwCallReceipt> results) {
		SwCallPlayAndDetected playAndDetected = task.getChannel().getPlayAndDetected();
		SwCallDetectNode logicNode = task.getChannel().getLogicNode();
		
		if (null == logicNode || null == playAndDetected) {
			logger.error("task: {} no logic or play define", task.getId());
			return results;
		}
		
		if (null == playAndDetected.getDetected() || !playAndDetected.isPlayFinished()) {
			logger.info("task: {} wait detect result or play finished", task.getId());
			return results;
		}

		SwCommonCallDialogNode nextNode = SwCallSceneLogic.getNextNode(logicNode.getNodeDefine(), playAndDetected.getDetected(), task.getDialog());
		
		if (null == nextNode) {
			logger.debug("task: {} node: {} no next node found try system", task.getId(), getNodeName(task));
			SwCommonCallDialogNode sysNode = getSysNode(task);
			
			if (null == sysNode) {
				results.add(SwCallSwitchReceiptFactory.createHangupCommand(task.getChannel().getUuid(), null));
				return results;
			}
			
			logger.info("task: {} node: {} sysnode: {} found", task.getId(), getNodeName(task), sysNode.getSysType());
			task.getChannel().setPlayAndDetected(sysNode);
			results.add(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task));
			
			return  results;
		}
		
		task.getChannel().gotoLogicNode(nextNode);
		results.add(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task));
		return results;
	}
	
	public static String getNodeName(SwCallTask task) {
		SwCallDetectNode node = task.getChannel().getLogicNode();
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
	
	private static SwCommonCallDialogNode getSysNode(SwCallTask task) {
		SwCallDetectNode logicNode = task.getChannel().getLogicNode();
		
		if (null == task.getDialog().getSyses() || null == logicNode) {
			logger.info("task: {} cur node: {}, no sys nodes", task.getId(), getNodeName(task));
			return  null;
		}
		
		SwCommonCallDialogNode byeNode = null;
		SwCommonCallDialogNode retryNode = null;
		
		for (SwCommonCallDialogNode sysNode: task.getDialog().getSyses()) {
			if (sysNode.getSysType().equalsIgnoreCase(SwCommonCallOperConstant.DIALOG_SYSTYPE_RETRY)) {
				retryNode = sysNode;
				continue;
			}
			
			if (sysNode.getSysType().equalsIgnoreCase(SwCommonCallOperConstant.DIALOG_SYSTYPE_BYE)) {
				byeNode = sysNode;
				continue;
			}
		}
		
		
		if (logicNode.getMaxRetryTimes() == 0) {
			logger.error("task: {} cur node: {}, no retry", task.getId(), getNodeName(task));
			return byeNode;
		}
		
		if (logicNode.getRetriedTimes() >= logicNode.getMaxRetryTimes()) {
			logger.error("task: {} cur node: {} max retry", task.getId(), getNodeName(task));
			return  byeNode;
		}
		
		return retryNode;
	}
}
