package com.lige.call.mgr.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.mgr.routes.SwCallContext;
import com.lige.call.mgr.routes.SwCallExecutorRouter;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class SwCallStartBean implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SwCallContext context;
	
	private SwCommonCallSessionCreatePojo pojo;
	
	public SwCallStartBean(SwCallContext context, SwCommonCallSessionCreatePojo pojo) {
		this.context = context;
		this.pojo = pojo;
	}

	@Override
	public void run() {
		if (null == context.getCamelContext() || null == context.getExecutorFactory() || null == context.getMqConfig()) {
			logger.error("Create call failed invalid context" );
			return;
		}
		SwCallExecutor executor = context.getExecutorFactory().create(pojo);

		if (executor == null) {
			logger.error("Create call failed" );
			return;
		}

		logger.debug("create new callexecutor {}", executor.toString());

		SwCallExecutorRouter callctrl = new SwCallExecutorRouter(pojo, executor, context);
		try {
			context.getCamelContext().addRoutes(callctrl);
		} catch (Exception e) {
			logger.error("create call failed with exception");
		}
		
	}

}
