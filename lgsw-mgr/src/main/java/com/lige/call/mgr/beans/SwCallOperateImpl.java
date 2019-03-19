package com.lige.call.mgr.beans;

import com.lige.call.api.exe.SwCallOperate;
import com.lige.call.mgr.routes.SwCallOperatePojo;

public class SwCallOperateImpl implements SwCallOperate{
	
	private SwCallOperatePojo oper;


	
	SwCallOperateImpl(SwCallOperatePojo oper) {
		this.oper = oper;
	}


	@Override
	public String toString() {
		return oper.toString();
	}

	@Override
	public String getId() {
		return oper.getId();
	}


	@Override
	public String getUid() {
		return oper.getUid();
	}


	@Override
	public String getCommand() {
		return oper.getCommand();
	}


	@Override
	public String getArg() {
		return oper.getArg();
	}


	@Override
	public String getUser() {
		return oper.getUser();
	}
	


}
