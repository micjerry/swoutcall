package com.lige.call.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.lige.call.mgr.config.ContextConfig;

@SpringBootApplication
public class SwCallMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwCallMgrApplication.class, args);	
		
		ApplicationContext context = SwCallMgrContextUtil.getApplicationContext();
		ContextConfig config = context.getBean(ContextConfig.class);
		config.configThreadPool();
	}
}
