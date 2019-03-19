package com.lige.call.mgr.routes;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.mgr.beans.HttpResponseBean;
import com.lige.call.mgr.beans.SwCallExecutorEventBean;
import com.lige.call.mgr.beans.SwCallOperateBean;
import com.lige.call.mgr.beans.SwCallTimerBean;
import com.lige.call.mgr.beans.SysControlBean;
import com.lige.call.mgr.config.RabbitmqConfig;
import com.lige.call.mgr.protocol.SwCallMgrProtocol;
import com.lige.common.call.api.SwCommonCallConstant;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;

final class SwCallExecutorRouter extends RouteBuilder {
	private final static Logger logger = LoggerFactory.getLogger(SwCallExecutorRouter.class);
	private String fromOperater;
	private String toSwitch;
	private String fromSwitch;
	private String toCdr;
	private String id;
	private SwCallOperateBean controlBean;
	private SwCallExecutorEventBean switchEventBean;
	private HttpResponseBean responseHandler;
	private SwCallTimerBean conferTimerBean;
	SwCallExecutorRouter(String id, SwCallExecutor control, RabbitmqConfig config, HttpResponseBean responseHandler) {

		StringBuffer fromOperaterBuffer = new StringBuffer("rabbitmq://localhost:");
		fromOperaterBuffer.append(config.getPort());
		fromOperaterBuffer.append("/" + config.getCallOperEx() + "?");
		fromOperaterBuffer.append("vhost=" + config.getCallOperVhost() + "&");
		fromOperaterBuffer.append("username=" + config.getUserName() + "&");
		fromOperaterBuffer.append("password=" + config.getPassword() + "&");
		fromOperaterBuffer.append("autoAck=true&");
		fromOperaterBuffer.append("exchangeType=direct&");
		fromOperaterBuffer.append("routingKey=" + id + "&");
		fromOperaterBuffer.append("durable=false&");
		fromOperaterBuffer.append("autoDelete=false&");
		fromOperaterBuffer.append("declare=true");
		this.fromOperater = fromOperaterBuffer.toString();
		
		StringBuffer toSwitchbuffer = new StringBuffer("rabbitmq://localhost:");
		toSwitchbuffer.append(config.getPort());
		toSwitchbuffer.append("/" + config.getSwCmdEx() + "?");
		toSwitchbuffer.append("vhost=" + config.getSwCmdVhost() + "&");
		toSwitchbuffer.append("username=" + config.getUserName() + "&");
		toSwitchbuffer.append("password=" + config.getPassword() + "&");
		toSwitchbuffer.append("exchangeType=direct&");
		toSwitchbuffer.append("routingKey=" + SwCommonCallConstant.CALL_CMD_ROUTING + "&");
		toSwitchbuffer.append("durable=false&");
		toSwitchbuffer.append("autoDelete=false&");
		toSwitchbuffer.append("skipQueueDeclare=true&");
		toSwitchbuffer.append("declare=true");
		this.toSwitch = toSwitchbuffer.toString();
		
		StringBuffer toCdrbuffer = new StringBuffer("rabbitmq://localhost:");
		toCdrbuffer.append(config.getPort());
		toCdrbuffer.append("/" + config.getSwCdrEx() + "?");
		toCdrbuffer.append("vhost=" + config.getSwCdrVhost() + "&");
		toCdrbuffer.append("username=" + config.getUserName() + "&");
		toCdrbuffer.append("password=" + config.getPassword() + "&");
		toCdrbuffer.append("exchangeType=direct&");
		toCdrbuffer.append("routingKey=" + SwCommonCallConstant.CALL_CDR_ROUTING + "&");
		toCdrbuffer.append("durable=false&");
		toCdrbuffer.append("autoDelete=false&");
		toCdrbuffer.append("skipQueueDeclare=true&");
		toCdrbuffer.append("declare=true");
		this.toCdr = toCdrbuffer.toString();
		
		StringBuffer fsntybuffer = new StringBuffer("rabbitmq://localhost:");
		fsntybuffer.append(config.getPort());
		fsntybuffer.append("/" + config.getSwEventEx() + "?");
		fsntybuffer.append("vhost=" + config.getSwEventVhost() + "&");
		fsntybuffer.append("username=" + config.getUserName() + "&");
		fsntybuffer.append("password=" + config.getPassword() + "&");
		fsntybuffer.append("exchangeType=direct&");
		fsntybuffer.append("routingKey=" + id + "&");
		fsntybuffer.append("durable=false&");
		fsntybuffer.append("autoDelete=false&");
		fsntybuffer.append("declare=true");
		this.fromSwitch = fsntybuffer.toString();
		this.conferTimerBean = new SwCallTimerBean(control);
		this.controlBean = new SwCallOperateBean(control);
		this.switchEventBean = new SwCallExecutorEventBean(control);
		this.responseHandler = responseHandler;
		this.id = id;
	}
	
	@Override
    public void configure() throws Exception {
		if (fromOperater == null || fromOperater.equals("")) {
			logger.error("Invalid from operate url.");
			throw new RuntimeException("Invalid call operate url.");
		}
		
		if (fromSwitch == null || fromSwitch.equals("")) {
			logger.error("Invalid switch url.");
			throw new RuntimeException("Invalid switch event url.");
		}
		
		Predicate http = header(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER).isEqualTo(SwCallMgrProtocol.SWCALL_COMMAND_HTTP);
		Predicate swcmd = header(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER).isEqualTo(SwCallMgrProtocol.SWCALL_COMMAND_SWITCH);
		Predicate cdr = header(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER).isEqualTo(SwCallMgrProtocol.SWCALL_COMMAND_CDR);
		Predicate sys = header(SwCallMgrProtocol.SWCALL_CMDTYPE_HEADER).isEqualTo(SwCallMgrProtocol.SWCALL_COMMAND_SYS);
		
		//create main route
		from(fromOperater).routeId(RoutePrefix.ROUTE_MAIN + this.id).unmarshal().json(JsonLibrary.Jackson, SwCallOperatePojo.class)
		.process(controlBean).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.ROUTE_HTTP + this.id)
		.when(swcmd).to("direct:"+RoutePrefix.ROUTE_MEDIA + this.id)
		.when(cdr).to("direct:"+RoutePrefix.ROUTE_CDR + this.id)
		.when(sys).to("direct:"+RoutePrefix.ROUTE_SYS + this.id)
		.end();
		
		//create switch event route
		from(fromSwitch).routeId(RoutePrefix.ROUTE_MEDIANTY + this.id).unmarshal().json(JsonLibrary.Jackson, SwCommonCallEslEventPojo.class)
		.process(switchEventBean).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.ROUTE_HTTP + this.id)
		.when(swcmd).to("direct:"+RoutePrefix.ROUTE_MEDIA + this.id)
		.when(cdr).to("direct:"+RoutePrefix.ROUTE_CDR + this.id)
		.when(sys).to("direct:"+RoutePrefix.ROUTE_SYS + this.id)
		.end();
		
		//create http route
		from("direct:"+RoutePrefix.ROUTE_HTTP + this.id).routeId(RoutePrefix.ROUTE_HTTP + this.id)
		.marshal().json(JsonLibrary.Jackson)
		.to("http4://127.0.0.1:8081/")
		.process(responseHandler);
		
		//create switch command route
		from("direct:"+RoutePrefix.ROUTE_MEDIA + this.id).routeId(RoutePrefix.ROUTE_MEDIA + this.id)
		.marshal().json(JsonLibrary.Jackson)
		.to(this.toSwitch);
		
		//create cdr route
		from("direct:"+RoutePrefix.ROUTE_CDR + this.id).routeId(RoutePrefix.ROUTE_CDR + this.id)
		.marshal().json(JsonLibrary.Jackson)
		.to(this.toCdr);
		
		//create sys command route
		logger.info("configure {}",id);
		SysControlBean sysBean = new SysControlBean(this.id);
		from("direct:"+RoutePrefix.ROUTE_SYS + this.id).routeId(RoutePrefix.ROUTE_SYS + this.id)
		.process(sysBean);	
		
		from("timer://foo?fixedRate=true&period=2000").routeId(RoutePrefix.ROUTE_TIMER + this.id).process(conferTimerBean).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.ROUTE_HTTP + this.id)
		.when(swcmd).to("direct:"+RoutePrefix.ROUTE_MEDIA + this.id)
		.when(cdr).to("direct:"+RoutePrefix.ROUTE_CDR + this.id)
		.when(sys).to("direct:"+RoutePrefix.ROUTE_SYS + this.id)
		.end();
    }
}
