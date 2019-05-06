package com.lige.call.mgr.routes;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.mgr.beans.SwCallExecutorEventBean;
import com.lige.call.mgr.beans.SwCallExecutorOperateBean;
import com.lige.call.mgr.beans.SwCallExecutorTimerBean;
import com.lige.call.mgr.beans.SwCallHttpResponseBean;
import com.lige.call.mgr.beans.SwCallReceiptSysHandleBean;
import com.lige.call.mgr.config.RabbitmqConfig;
import com.lige.call.mgr.protocol.SwCallMgrProtocol;
import com.lige.call.mgr.routes.RoutePrefix.RouteMain;
import com.lige.call.mgr.routes.RoutePrefix.RouteSecond;
import com.lige.common.call.api.SwCommonCallConstant;
import com.lige.common.call.api.config.mq.SwCommonMqConfig;
import com.lige.common.call.api.esl.SwCommonCallEslEventPojo;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;
import com.lige.common.call.api.oper.SwCommonCallSessionOperPojo;

public final class SwCallExecutorRouter extends RouteBuilder {
	private final static Logger logger = LoggerFactory.getLogger(SwCallExecutorRouter.class);
	private String fromOperater;
	private String toSwitch;
	private String fromSwitch;
	private String toCdr;
	private String id;
	private String groupId;
	private SwCallContext callContext;
	private SwCallExecutorOperateBean swOperateHandler;
	private SwCallExecutorEventBean switchEventBean;
	private SwCallHttpResponseBean responseHandler;
	private SwCallExecutorTimerBean conferTimerBean;
	public SwCallExecutorRouter(SwCommonCallSessionCreatePojo pojo, SwCallExecutor control, SwCallContext callContext) {
		this.callContext = callContext;
		this.id = pojo.getTaskid();
		this.groupId = pojo.getGroupid();
		this.responseHandler = callContext.getResponseBean();
		RabbitmqConfig config = callContext.getMqConfig();
		String cmdRouteKey = SwCommonCallConstant.CALL_CMD_ROUTING + pojo.getSwid();
		StringBuffer fromOperaterBuffer = new StringBuffer(SwCommonMqConfig.MQ_SYS_URL);
		fromOperaterBuffer.append(SwCommonMqConfig.MQ_SYS_PORT);
		fromOperaterBuffer.append("/" + SwCommonMqConfig.MQ_EXCHANGE_OPER + "?");
		fromOperaterBuffer.append("vhost=" + SwCommonMqConfig.MQ_VHOST_OPER + "&");
		fromOperaterBuffer.append("username=" + config.getUserName() + "&");
		fromOperaterBuffer.append("password=" + config.getPassword() + "&");
		fromOperaterBuffer.append("autoAck=true&");
		fromOperaterBuffer.append("exchangeType=direct&");
		fromOperaterBuffer.append("routingKey=" + this.id + "&");
		fromOperaterBuffer.append("durable=false&");
		fromOperaterBuffer.append("autoDelete=false&");
		fromOperaterBuffer.append("declare=true");
		this.fromOperater = fromOperaterBuffer.toString();
		
		StringBuffer toSwitchbuffer = new StringBuffer(SwCommonMqConfig.MQ_SYS_URL);
		toSwitchbuffer.append(SwCommonMqConfig.MQ_SYS_PORT);
		toSwitchbuffer.append("/" + SwCommonMqConfig.MQ_EXCHANGE_CMD + "?");
		toSwitchbuffer.append("vhost=" + SwCommonMqConfig.MQ_VHOST_CMD + "&");
		toSwitchbuffer.append("username=" + config.getUserName() + "&");
		toSwitchbuffer.append("password=" + config.getPassword() + "&");
		toSwitchbuffer.append("exchangeType=direct&");
		toSwitchbuffer.append("routingKey=" + cmdRouteKey + "&");
		toSwitchbuffer.append("durable=false&");
		toSwitchbuffer.append("autoDelete=false&");
		toSwitchbuffer.append("skipQueueDeclare=true&");
		toSwitchbuffer.append("declare=true");
		this.toSwitch = toSwitchbuffer.toString();
		
		StringBuffer toCdrbuffer = new StringBuffer(SwCommonMqConfig.MQ_SYS_URL);
		toCdrbuffer.append(SwCommonMqConfig.MQ_SYS_PORT);
		toCdrbuffer.append("/" + SwCommonMqConfig.MQ_EXCHANGE_CDR + "?");
		toCdrbuffer.append("vhost=" + SwCommonMqConfig.MQ_VHOST_CDR + "&");
		toCdrbuffer.append("username=" + config.getUserName() + "&");
		toCdrbuffer.append("password=" + config.getPassword() + "&");
		toCdrbuffer.append("exchangeType=direct&");
		toCdrbuffer.append("routingKey=" + SwCommonCallConstant.CALL_CDR_ROUTING + "&");
		toCdrbuffer.append("durable=false&");
		toCdrbuffer.append("autoDelete=false&");
		toCdrbuffer.append("skipQueueDeclare=true&");
		toCdrbuffer.append("declare=true");
		this.toCdr = toCdrbuffer.toString();
		
		StringBuffer fromSwitchSb = new StringBuffer(SwCommonMqConfig.MQ_SYS_URL);
		fromSwitchSb.append(SwCommonMqConfig.MQ_SYS_PORT);
		fromSwitchSb.append("/" + SwCommonMqConfig.MQ_EXCHANGE_EVENT + "?");
		fromSwitchSb.append("vhost=" + SwCommonMqConfig.MQ_VHOST_EVENT + "&");
		fromSwitchSb.append("username=" + config.getUserName() + "&");
		fromSwitchSb.append("password=" + config.getPassword() + "&");
		fromSwitchSb.append("exchangeType=direct&");
		fromSwitchSb.append("routingKey=" + this.id + "&");
		fromSwitchSb.append("durable=false&");
		fromSwitchSb.append("autoDelete=false&");
		fromSwitchSb.append("declare=true");
		this.fromSwitch = fromSwitchSb.toString();
		this.conferTimerBean = new SwCallExecutorTimerBean(control);
		this.swOperateHandler = new SwCallExecutorOperateBean(control);
		this.switchEventBean = new SwCallExecutorEventBean(control);
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
		
		//create http route
		from("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_HTTP, this.id)).routeId(RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_HTTP, this.id))
		.marshal().json(JsonLibrary.Jackson)
		.to("http4://127.0.0.1:8081/")
		.process(responseHandler);
		
		//create switch command route
		from("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SWCMD, this.id)).routeId(RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SWCMD, this.id))
		.marshal().json(JsonLibrary.Jackson)
		.to(this.toSwitch);
		
		//create cdr route
		from("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_CDR, this.id)).routeId(RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_CDR, this.id))
		.marshal().json(JsonLibrary.Jackson)
		.to(this.toCdr);
		
		//create sys command route
		logger.info("configure {}",id);
		SwCallReceiptSysHandleBean sysBean = new SwCallReceiptSysHandleBean(this.id, this.groupId, callContext);
		from("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SYS, this.id)).routeId(RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SYS, this.id))
		.process(sysBean);	
		
		//create main route
		from(fromOperater).routeId(RoutePrefix.createMainRoute(RouteMain.ROUTE_MAIN_OPER, this.id)).unmarshal().json(JsonLibrary.Jackson, SwCommonCallSessionOperPojo.class)
		.process(swOperateHandler).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_HTTP, this.id))
		.when(swcmd).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SWCMD, this.id))
		.when(cdr).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_CDR, this.id))
		.when(sys).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SYS, this.id))
		.end();
		
		//create switch event route
		from(fromSwitch).routeId(RoutePrefix.createMainRoute(RouteMain.ROUTE_MAIN_SWITCHEVENT, this.id)).unmarshal().json(JsonLibrary.Jackson, SwCommonCallEslEventPojo.class)
		.process(switchEventBean).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_HTTP, this.id))
		.when(swcmd).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SWCMD, this.id))
		.when(cdr).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_CDR, this.id))
		.when(sys).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SYS, this.id))
		.end();
		
		from("timer://foo?fixedRate=true&period=1000").routeId(RoutePrefix.createMainRoute(RouteMain.ROUTE_MAIN_TIMER, this.id)).process(conferTimerBean).split(body())
		.choice()
		.when(http).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_HTTP, this.id))
		.when(swcmd).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SWCMD, this.id))
		.when(cdr).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_CDR, this.id))
		.when(sys).to("direct:"+RoutePrefix.createSecondRoute(RouteSecond.ROUTE_SECOND_SYS, this.id))
		.end();
    }
}
