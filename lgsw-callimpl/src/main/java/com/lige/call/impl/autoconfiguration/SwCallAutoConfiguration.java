package com.lige.call.impl.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.beans.SwCallExecutorFactoryImpl;


@Configuration
public class SwCallAutoConfiguration {	
	@Bean
	@ConditionalOnMissingBean
	public SwCallExecutorFactory getCallExecutorFactory() {
		SwCallExecutorFactoryImpl factory = new SwCallExecutorFactoryImpl();
		return factory;
	}
}
