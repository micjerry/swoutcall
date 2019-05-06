package com.lige.call.mgr.routes;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.mgr.beans.SwCallHttpResponseBean;
import com.lige.call.mgr.config.RabbitmqConfig;

@Component
public class SwCallContext {
	
	@Autowired
	private CamelContext camelContext;

	@Autowired
	private SwCallExecutorFactory executorFactory;

	@Autowired
	private RabbitmqConfig mqConfig;

	@Autowired
	private SwCallHttpResponseBean responseBean;
	
	
	public CamelContext getCamelContext() {
		return camelContext;
	}

	public SwCallExecutorFactory getExecutorFactory() {
		return executorFactory;
	}

	public RabbitmqConfig getMqConfig() {
		return mqConfig;
	}

	public SwCallHttpResponseBean getResponseBean() {
		return responseBean;
	}

}
