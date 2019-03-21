package com.lige.call.mgr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("rabbitconfig")
public class RabbitmqConfig {
	@Value("${rabbitmq.username}")
	private String username;
	
	@Value("${rabbitmq.password}")
	private String password;

	
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
