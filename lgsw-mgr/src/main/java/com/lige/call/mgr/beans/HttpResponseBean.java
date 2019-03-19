package com.lige.call.mgr.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.lige.call.api.cmd.SwCallReceiptCallback;
import com.lige.call.mgr.protocol.SwCallMgrProtocol;

@Component("httpresponseBean")
public class HttpResponseBean implements Processor {
	public void process(Exchange exchange) throws Exception {
		SwCallReceiptCallback callback = exchange.getIn().
				getHeader(SwCallMgrProtocol.SWCALL_CALLBACK_HEADER, SwCallReceiptCallback.class);
		if (callback == null) {
			return;
		}
		
		callback.onResult(exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class), 
				exchange.getIn().getBody(String.class));
	}
}
