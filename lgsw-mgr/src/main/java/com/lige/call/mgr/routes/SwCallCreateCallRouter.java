package com.lige.call.mgr.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lige.call.mgr.scheduler.CallSchedulerMgr;
import com.lige.common.call.api.oper.SwCommonCallSessionCreateMultiPojo;

@Component
public class SwCallCreateCallRouter extends RouteBuilder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SwCallContext callContext;

	@Value("${outcall.host}")
	private String host;

	@Value("${outcall.port}")
	private String port;

	@Override
	public void configure() throws Exception {
		StringBuilder fromUrl = new StringBuilder("netty-http:http://");
		fromUrl.append(host);
		fromUrl.append(":");
		fromUrl.append(port);
		fromUrl.append("/swcall/createcall");
		from(fromUrl.toString()).unmarshal().json(JsonLibrary.Jackson, SwCommonCallSessionCreateMultiPojo.class)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						Message message = exchange.getIn();
						SwCommonCallSessionCreateMultiPojo body = message.getBody(SwCommonCallSessionCreateMultiPojo.class);
						
						if (!CallSchedulerMgr.addScheduler(body)) {
							logger.error("invalid req");
							return;
						}
						
						CallSchedulerMgr.commitNextCall(body.getGroupid(), callContext);
					}
				});

	}
}
