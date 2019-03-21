package com.lige.call.mgr.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.cmd.SwCallReceipt;
import com.lige.call.api.cmd.SwCallReceiptHttp;
import com.lige.call.mgr.protocol.SwCallMgrProtocol;

public class SwCallReceiptFormater {
	private static final Logger logger = LoggerFactory.getLogger(SwCallReceiptFormater.class);
	
	public static List<Message> format(List<SwCallReceipt> commands) {
		if (null == commands) {
			return null;
		}
		List<Message> msglist = new ArrayList<Message>();
		for (SwCallReceipt command : commands) {
			Message msg = new DefaultMessage();
			msg.setBody(command.getBody());
			msg.setHeader(SwCallMgrProtocol.SWCALL_CALLBACK_HEADER, command.getCallback());
			
			switch (command.getType()) {

			case RECEIPT_TYPE_HTTP:
				SwCallReceiptHttp httpcommand = (SwCallReceiptHttp)command;
				if (httpcommand.getHost() == null || httpcommand.getHost().equals("") || 
						httpcommand.getMethod() == null || httpcommand.getMethod().equals("")) {
					msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_BAD);
					logger.error("invalid  name = " + command.getName() + " id = " + command.getId());
					continue;
				}
				
				msg.setHeader(Exchange.HTTP_URI, httpcommand.getHost());
				msg.setHeader(Exchange.HTTP_METHOD, httpcommand.getMethod());
				msg.setHeader(Exchange.HTTP_PATH, httpcommand.getUrl());
				msg.setHeader(Exchange.CONTENT_TYPE, SwCallMgrProtocol.SWCALL_DEFAULT_CONTENT_TYPE);
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_HTTP);
				
				break;
			case RECEIPT_TYPE_CACHE:
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_CACHE);
				break;
			case RECEIPT_TYPE_SWITCH:
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_SWITCH);
				msg.setHeader("rabbitmq.EXPIRATION", 10000);
				break;
			case RECEIPT_TYPE_CDR:
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_CDR);
				break;
			case RECEIPT_TYPE_SYS:
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_SYS);
				break;
			default:
				msg.setHeader(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER, SwCallMgrProtocol.SWCALL_COMMAND_BAD);
				break;	
			}
			
			msglist.add(msg);
		}
		
		return msglist;
	}
}
