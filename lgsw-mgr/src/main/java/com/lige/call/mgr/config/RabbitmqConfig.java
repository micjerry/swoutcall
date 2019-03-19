package com.lige.call.mgr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("rabbitconfig")
public class RabbitmqConfig {
	@Value("${rabbitmq.username}")
	private String username;
	
	@Value("${rabbitmq.password}")
	private String password;
	
	@Value("${rabbitmq.port}")
	private String port;
	
	@Value("${rabbitmq.opervhost}")
	private String callOperVhost;
	
	@Value("${rabbitmq.swcmdvhost}")
	private String swCmdVhost;
	
	@Value("${rabbitmq.sweventvhost}")
	private String swEventVhost;
	
	@Value("${rabbitmq.cdrvhost}")
	private String swCdrVhost;

	@Value("${rabbitmq.operex}")
	private String callOperEx;

	@Value("${rabbitmq.swcmdex}")
	private String swCmdEx;
	
	@Value("${rabbitmq.sweventex}")
	private String swEventEx;
	
	@Value("${rabbitmq.cdrex}")
	private String swCdrEx;
	
	public String getCallOperVhost() {
		return callOperVhost;
	}

	public String getCallOperEx() {
		return callOperEx;
	}

	public String getSwCmdEx() {
		return swCmdEx;
	}

	public String getSwEventEx() {
		return swEventEx;
	}

	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPort() {
		return port;
	}
	
	public String getSwCmdVhost() {
		return swCmdVhost;
	}
	
	public String getSwEventVhost() {
		return swEventVhost;
	}
	
	public String getSwCdrVhost() {
		return swCdrVhost;
	}

	public String getSwCdrEx() {
		return swCdrEx;
	}
}
