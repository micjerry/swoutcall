package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.call.impl.api.SwCallTask.CallStage;
import com.lige.call.impl.receiptswitch.SwCallSwitchReceiptFactory;
import com.lige.call.impl.tools.ReceiptLoader;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

@Component
public class SwEventChannelAnswerHandler implements SwCallSwitchEventHandler {

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		task.setStage(CallStage.CALL_STAGE_ANSWERED);
		
		SwCommonCallDialog dialog = task.getDialog();
		List<SwCommonCallDialogNode> nodes = dialog.getNodes();
		SwCommonCallDialogNode firstNode = nodes.get(0);
		
		task.setCurNode(firstNode);		
		return ReceiptLoader.loadReceipt(SwCallSwitchReceiptFactory.createPlayAndDetectCommand(task.getUuid(), firstNode));
	}

}
