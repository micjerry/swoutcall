package com.lige.call.impl.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogBranch;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallOperConstant;

public class SwCallSceneLogic {
	private static final Logger logger = LoggerFactory.getLogger(SwCallSceneLogic.class);
	
	public static SwCommonCallDialogNode getNextNode(SwCommonCallDialogNode curNode, String detected, SwCommonCallDialog dialog) {
		if (null == curNode || null == dialog) {
			logger.error("invalid request curNode or dialog is null");
			return null;
		}
		
		if (null == curNode.getBranchs() || curNode.getBranchs().isEmpty()) {
			logger.info("curnode: {} has not branches", curNode.getName());
			return null;
		}

		SwCommonCallDialogBranch matchedBranch = null;
		String detectedPinyin = PinyinTool.getPinyin(detected);
		
		for (SwCommonCallDialogBranch branch: curNode.getBranchs() ) {
			if ("*".equals(branch.getMatch())) {
				matchedBranch = branch;
				continue;
			}
			
			String[] matchs = branch.getMatch().split("-");
			for (String match:matchs) {
				String matchPinyin = PinyinTool.getPinyin(match);
				if (detectedPinyin.contains(matchPinyin)) {
					matchedBranch = branch;
					break;
				}
			}	
		}
		
		if (null == matchedBranch) {
			if (null == dialog.getSyses() || curNode.getRetryTimes() == 0) {
				logger.error("no matched branch found for speech {} node {}", detected, curNode.getName());
				return null;
			}
			
			for (SwCommonCallDialogNode sysNode: dialog.getSyses()) {
				if (sysNode.getSysType() == SwCommonCallOperConstant.DIALOG_SYSTYPE_RETRY) {
					
				}
			}
			
		}
		
		for (SwCommonCallDialogNode node : dialog.getNodes()) {
			if (node.getName().equalsIgnoreCase(matchedBranch.getNode())) {
				return node;
			}
		}
		
		return null;
	}
	
}
