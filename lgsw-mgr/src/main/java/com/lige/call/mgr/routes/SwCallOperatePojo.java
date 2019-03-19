package com.lige.call.mgr.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SwCallOperatePojo {
	private String uid;
	private String id;
	private String user;
	private String command;
	private String arg;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id = " + id + " command = " + command + " arg" + arg;
	}

	public SwCallOperatePojo() {
		
	}
	
	public String getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
