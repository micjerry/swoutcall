package com.lige.call.impl.receiptswitch;

import com.lige.call.api.cmd.SwCallReceiptSwitch;
import com.lige.common.call.api.esl.SwCommonCallEslCommandPojo;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;

public class SwCallSwitchReceiptFactory {
	public static final String COMMAND_NAME_EXECUTE = "execute";
	public static final String COMMAND_NAME_SWITCH_CALL = "originate";
	public static final String COMMAND_NAME_SWITCH_HANGUP = "hangup";
	
	public static final String APP_NAME_SWITCH_PLAYANDETECT = "play_and_detect_speech";
	public static final String APP_NAME_SWITCH_PLAY = "playback";
	public static final String RECEIPT_NAME_SWITCH_DETECT = "detect";

	public static SwCallReceiptSwitch createPlayAndDetectCommand(String uuid, SwCommonCallDialogNode node) {
		SwCallReceiptSwitchImpl command = new SwCallReceiptSwitchImpl(uuid, APP_NAME_SWITCH_PLAYANDETECT);
		SwCommonCallEslCommandPojo playPojo = new SwCommonCallEslCommandPojo();
		playPojo.setUuid(uuid);
		playPojo.setCallCmd(COMMAND_NAME_EXECUTE);
		playPojo.setAppName(APP_NAME_SWITCH_PLAYANDETECT);
		
		StringBuilder argsb = new StringBuilder();
		argsb.append(node.getPlay());
		argsb.append("detect:unimrcp {start-input-timers=false,no-input-timeout=5000,recognition-timeout=5000}hello");
		playPojo.setAppArg(argsb.toString());
		
		command.addMessage(playPojo);
		return command;
	}
	
	public static SwCallReceiptSwitch createHangupCommand(String uuid, String cause) {
		SwCallReceiptSwitchImpl command = new SwCallReceiptSwitchImpl(uuid, COMMAND_NAME_SWITCH_HANGUP);
		SwCommonCallEslCommandPojo hangupPojo = new SwCommonCallEslCommandPojo();
		hangupPojo.setUuid(uuid);
		hangupPojo.setCallCmd(COMMAND_NAME_SWITCH_HANGUP);
		if (null != cause && !"".equals(cause)) {
			hangupPojo.setHangupCause(cause);
		}
		
		command.addMessage(hangupPojo);
		return command;
	}
	
	public static SwCallReceiptSwitch createCallCommand(String uuid, String calleeNumber, String callerNumber, String host, String gateWay) {
		SwCallReceiptSwitchImpl command = new SwCallReceiptSwitchImpl(uuid, COMMAND_NAME_SWITCH_CALL);
		SwCommonCallEslCommandPojo callCmdPojo = new SwCommonCallEslCommandPojo();
		callCmdPojo.setCmd(COMMAND_NAME_SWITCH_CALL);
		StringBuilder callArgSb = new StringBuilder();
		callArgSb.append("{");
		callArgSb.append("callseqid=");
		callArgSb.append(uuid);
		if ((null != callerNumber) && (!"".equals(callerNumber))) {
			callArgSb.append(",");
			callArgSb.append("origination_caller_id_number=");
			callArgSb.append(callerNumber);
		}
		callArgSb.append("}");
		if ((null != gateWay) && (!"".equals(gateWay))) {
			callArgSb.append("sofia/gateway/");
			callArgSb.append(gateWay);
			callArgSb.append("/");
			callArgSb.append(calleeNumber);
		} else {
			callArgSb.append("sofia/");
			callArgSb.append(host);
			callArgSb.append("/");
			callArgSb.append(calleeNumber);
		}
		
		callArgSb.append(" 5001 XML default CALLER_ID_NAME CALLER_ID_NUMBER");
		callCmdPojo.setArg(callArgSb.toString());
		
		command.addMessage(callCmdPojo);	
		return command;
	}

}
