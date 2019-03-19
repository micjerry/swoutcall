package com.lige.call.mgr.routes;

import java.util.ArrayList;
import java.util.List;

public class RoutePrefix {
	public static final String ROUTE_MAIN = "ccmain";
	public static final String ROUTE_MEDIANTY ="medianty";
	public static final String ROUTE_HTTP = "http";
	public static final String ROUTE_TIMER = "timer";
	public static final String ROUTE_MEDIA = "media";
	public static final String ROUTE_CDR = "cdr";
	public static final String ROUTE_SYS = "sys";
	
	public static List<String> getRouteList(String confId) {
		List<String> routeList = new ArrayList<String>();
		routeList.add(ROUTE_MAIN + confId);
		routeList.add(ROUTE_MEDIANTY + confId);
		routeList.add(ROUTE_HTTP + confId);
		routeList.add(ROUTE_TIMER + confId);
		routeList.add(ROUTE_MEDIA + confId);
		routeList.add(ROUTE_SYS + confId);
		
		return routeList;
	}
}
