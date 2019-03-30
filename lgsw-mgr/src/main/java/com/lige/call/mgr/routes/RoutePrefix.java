package com.lige.call.mgr.routes;

import java.util.ArrayList;
import java.util.List;

public class RoutePrefix {
	public static final String ROUTE_MAIN = "opercmd";
	public static final String ROUTE_SWITCHEVENT ="swevent";
	public static final String ROUTE_HTTP = "http";
	public static final String ROUTE_TIMER = "timer";
	public static final String ROUTE_SWITCHCMD = "swcmd";
	public static final String ROUTE_CDR = "cdr";
	public static final String ROUTE_SYS = "sys";
	
	public enum RouteMain {
		ROUTE_MAIN_OPER("opercmd"),
		ROUTE_MAIN_SWITCHEVENT("swevent"),
		ROUTE_MAIN_TIMER("timer");
		
		private final String value;
		
		RouteMain(String value) {
			this.value = value;
		}
		
		public String toString() {
			return this.value;
		}
	}
	
	public enum RouteSecond {
		ROUTE_SECOND_SWCMD("swcmd"),
		ROUTE_SECOND_HTTP("http"),
		ROUTE_SECOND_CDR("cdr"),
		ROUTE_SECOND_SYS("sys");
		
		private final String value;
		
		RouteSecond(String value) {
			this.value = value;
		}
		
		public String toString() {
			return this.value;
		}
	}
	
	public static String createMainRoute(RouteMain mainRoute, String id) {
		return mainRoute.toString() + id;
	}
	
	public static String createSecondRoute(RouteSecond second, String id) {
		return second.toString() + id;
	}
	
	public static List<String> getRouteNames(String id) {
		List<String> routeList = new ArrayList<String>();
		for (RouteMain mainRoute: RouteMain.values()) {
			routeList.add(mainRoute.toString() + id);
		}
		
		for (RouteSecond secondRoute: RouteSecond.values()) {
			routeList.add(secondRoute.toString() + id);
		}
		return routeList;
	}
}
