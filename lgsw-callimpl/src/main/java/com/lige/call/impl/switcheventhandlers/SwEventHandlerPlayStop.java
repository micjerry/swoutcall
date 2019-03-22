package com.lige.call.impl.switcheventhandlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallSwitchEventHandler;
import com.lige.call.impl.api.SwCallTask;
import com.lige.common.call.api.esl.SwCommonCallEslConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventParser;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public class SwEventHandlerPlayStop implements SwCallSwitchEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SwCallReceipt> handle(SwCommonCallEslEventPojo event, SwCallTask task) {
		
		String playFile = SwCommonCallEslEventParser.getCustomHeader(event, SwCommonCallEslConstant.ESLHEADER_PLAY_FILE);
		SwCallDetectNode node = task.getCurNode();
		
		if (null == node || null == playFile) {
			logger.error("can not process play stop event no node found");
			return null;
		}
		
		SwCommonCallDialogNode nodeDefine = node.getNodeDefine();
		
		if (null == nodeDefine) {
			logger.error("can not process play stop event no node define found");
			return null;
		}
		
		if (playFile.contains(nodeDefine.getPlay())) {
			logger.info("task: {} node: {} file: {} play finished", task.getId(), NextNodeUtil.getNodeName(task), nodeDefine.getPlay());
			node.setPlayFinished(true);
			return NextNodeUtil.nextStep(task);
		} else {
			logger.error("unkown play stop event");
		}
		
		return null;
	}

}
