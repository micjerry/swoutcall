package com.lige.call.impl.asr;

import com.lige.call.impl.api.SwCallDetectNode;
import com.lige.call.impl.api.SwCallPlayAndDetected;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public class AsrFactory {
	public static SwCallPlayAndDetected makePlayAndDetected(SwCommonCallDialogNode node, int seq) {
		return new SwCallPlayAndDetectedImpl(node, seq);
	}
	
	public static SwCallDetectNode makeLogicNode(SwCommonCallDialogNode node) {
		return new SwCallDetectNodeImpl(node);
	}
}
