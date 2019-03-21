package com.lige.call.mgr.routes;

import org.apache.camel.CamelContext;
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

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.mgr.beans.SwCallHttpResponseBean;
import com.lige.call.mgr.config.RabbitmqConfig;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

@Component
public class SwCallCreateCallRouter extends RouteBuilder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CamelContext context;
	
	@Autowired
	private SwCallExecutorFactory factory;
	
	@Autowired
	private RabbitmqConfig config;
	
	@Autowired
	private SwCallHttpResponseBean responseHandler;
	
	
	@Value("${conf.cc.host}")
	private String host;
	
	@Value("${conf.cc.port}")
	private String port;

	@Override
    public void configure() throws Exception {
		StringBuilder fromUrl = new StringBuilder("netty-http:http://");
		fromUrl.append(host);
		fromUrl.append(":");
		fromUrl.append(port);
		fromUrl.append("/swcall/createcall");
		from(fromUrl.toString()).unmarshal().json(JsonLibrary.Jackson, SwCommonCallSessionCreatePojo.class)
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
                logger.debug("@@@process create call req start");
				Message message = exchange.getIn();
				SwCommonCallSessionCreatePojo body = message.getBody(SwCommonCallSessionCreatePojo.class);

				SwCallExecutor executor = factory.create(body);
				
				if (executor == null) {
					logger.error("Create call failed" + exchange.getIn().getHeader("CamelHttpQuery", String.class));
					return;
				}
				
				logger.debug("@@@ create new callexecutor {}", executor.toString());
				
				String id = body.getTaskid();
				
				if (id == null || id.equals("")) {
					logger.error("Failed to create call no calltask id");
					return;
				}
				SwCallExecutorRouter callctrl = new SwCallExecutorRouter(id, executor, config, body.getSwid(), responseHandler);
				context.addRoutes(callctrl);
			}
		});

    }
}
