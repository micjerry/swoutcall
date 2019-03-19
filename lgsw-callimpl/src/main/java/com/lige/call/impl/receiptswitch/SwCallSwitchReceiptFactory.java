package com.lige.call.impl.receiptswitch;


import com.lige.call.api.cmd.SwCallReceiptSwitch;
import com.lige.common.call.api.esl.SwCommonCallEslCommandPojo;

public class SwCallSwitchReceiptFactory {
	public static final String RECEIPT_NAME_SWITCH_CALL = "call";
	public static final String RECEIPT_NAME_SWITCH_HANGUP = "hangup";
	public static final String RECEIPT_NAME_SWITCH_PLAY = "play";
	public static final String RECEIPT_NAME_SWITCH_DETECT = "detect";
	
	public static SwCallReceiptSwitch createHangupCommand(String uuid, String cause) {
		SwCallReceiptSwitchImpl command = new SwCallReceiptSwitchImpl(uuid, RECEIPT_NAME_SWITCH_HANGUP);
		//TODO add hangup esl message
		return command;
	}
	
	public static SwCallReceiptSwitch createCallCommand(String uuid, String calleeNumber, String callerNumber, String gateWay) {
		SwCallReceiptSwitchImpl command = new SwCallReceiptSwitchImpl(uuid, RECEIPT_NAME_SWITCH_CALL);
		SwCommonCallEslCommandPojo callCmdPojo = new SwCommonCallEslCommandPojo();
		callCmdPojo.setCallCmd("originate");
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
			callArgSb.append(calleeNumber);
		}
		callArgSb.append(" 5001 XML default CALLER_ID_NAME CALLER_ID_NUMBER");
		
		
		
		return command;
	}

}
