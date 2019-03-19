package com.lige.call.mgr.beans;

import java.util.List;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.mgr.routes.RoutePrefix;

public class SwCallStopBean implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String id;
	private CamelContext camelContext;
	
	SwCallStopBean(CamelContext camelContext, String id) {
		this.camelContext = camelContext;
		this.id = id;
	}
	
	@Override
	public void run() {
		logger.info("@@@ConferenceStopBean.run is called...");
		try {
			List<String> routes = RoutePrefix.getRouteList(this.id);
			for (String routeId: routes) {
				camelContext.stopRoute(routeId);
				camelContext.removeRoute(routeId);
			}
		} catch (Exception e) {
			logger.error("stop route failed confid = " + this.id);
		}
	}
}
