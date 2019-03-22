package com.lige.call.impl.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogBranch;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

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
		
		for (SwCommonCallDialogBranch branch: curNode.getBranchs() ) {
			if ("*".equals(branch.getMatch())) {
				matchedBranch = branch;
				continue;
			}
			
			String[] matchs = branch.getMatch().split("-");
			for (String match:matchs) {
				if (detected.contains(match)) {
					matchedBranch = branch;
					break;
				}
			}	
		}
		
		if (null == matchedBranch) {
			logger.error("no matched branch found for speech {} node {}", detected, curNode.getName());
			return null;
		}
		
		for (SwCommonCallDialogNode node : dialog.getNodes()) {
			if (node.getName().equalsIgnoreCase(matchedBranch.getNode())) {
				return node;
			}
		}
		
		return null;
	}
	
}
