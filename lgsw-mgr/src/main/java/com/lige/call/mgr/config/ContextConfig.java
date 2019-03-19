package com.lige.call.mgr.config;

import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.lige.call.mgr.SwCallMgrContextUtil;

@Component
public class ContextConfig {

	public void configThreadPool() {
		ApplicationContext ctx = SwCallMgrContextUtil.getApplicationContext();
		SpringCamelContext context = ctx.getBean(SpringCamelContext.class);
		ExecutorServiceManager manager = context.getExecutorServiceManager();
		ThreadPoolProfile profile = manager.getDefaultThreadPoolProfile();
		profile.setMaxPoolSize(50);
	}
}
