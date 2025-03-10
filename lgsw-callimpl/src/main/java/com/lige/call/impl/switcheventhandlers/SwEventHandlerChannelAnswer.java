package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallState;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

class SwEventHandlerChannelAnswer implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		NextNodeUtil.refreshChannel(SwCallState.CALLING, task, event);
		
		SwCommonCallDialog dialog = task.getDialog();
		SwCommonCallDialogNode firstNode = null;
		if (null == dialog.getNodes() || dialog.getNodes().isEmpty()) {
			logger.error("task: {} answer invalid dialog", task.getId());
			return null;
		}
		
		for (SwCommonCallDialogNode node: dialog.getNodes()) {
			if (node.isFirst()) {
				firstNode = node;
				break;
			}			
		}

		if (null == firstNode) {
			logger.error("task: {} no first node found", task.getId());
			firstNode = dialog.getNodes().get(0);
		}
		
		task.getChannel().gotoLogicNode(firstNode);
		return ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task));
	}

	@Override
	public String getName() {
		return SwCommonCallEslConstant.ESLEVENT_CHANNEL_ANSWER;
	}

}
