package com.lige.call.mgr.scheduler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.call.mgr.beans.SwCallStartBean;
import com.lige.call.mgr.routes.SwCallContext;
import com.lige.call.mgr.utils.ReqCheckUtil;
import com.lige.call.mgr.utils.SwCallExecutorService;
import com.lige.common.call.api.oper.SwCommonCallSessionCreateMultiPojo;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class CallSchedulerMgr {
	private static final Logger logger = LoggerFactory.getLogger(CallSchedulerMgr.class);
	
	private static final String VALID_NUMBER_DEFAULT = "17301946084";
	
	private static final int MAX_CALL_NUMBERS = 1000;
	
	private static ConcurrentHashMap<String, CallScheduler> schedulers = new ConcurrentHashMap<String, CallScheduler>();
	
	public static boolean addScheduler(SwCommonCallSessionCreateMultiPojo pojo) {
		if (null == pojo.getGroupid() || "".equals(pojo.getGroupid())) {
			logger.error("invalid req no groupid found");
			return false;
		}
		
		SwCommonCallSessionCreatePojo createPojo = ReqCheckUtil.makeSession(pojo);
		createPojo.setCalleenumber(VALID_NUMBER_DEFAULT);
		
		if (!ReqCheckUtil.checkReq(createPojo)) {
			logger.error("invalid req create task failed");
			return false;
		}
		
		List<String> calleeNumbers = pojo.getCalleenumbers();
		
		if (null == calleeNumbers || calleeNumbers.isEmpty()) {
			logger.error("invalid req no numbers create task failed");
			return false;
		}
		
		if (calleeNumbers.size() > MAX_CALL_NUMBERS) {
			logger.error("invalid req numbers count: {} exceed limit: {}", calleeNumbers.size(), MAX_CALL_NUMBERS);
			return false;
		}
		
		Set<String> formatedNumbers = new HashSet<>();
		for (String calleeNumber: calleeNumbers) {
			String formatedNumber = ReqCheckUtil.formatNumber(calleeNumber);
			if (null == formatedNumber || "".equals(formatedNumber)) {
				logger.error("invalid number: {} create task failed", calleeNumber);
				return false;
			}
			
			formatedNumbers.add(formatedNumber);
			
		}
		
		if (schedulers.get(pojo.getGroupid()) != null) {
			logger.error("the task with same groupid is running");
			return false;
		}
		
		CallScheduler scheduler = new CallScheduler(pojo.getGroupid(), formatedNumbers, createPojo);
		schedulers.put(scheduler.getId(), scheduler);
		return true;
	}
	
	
	public static void commitNextCall(String schedulerId, SwCallContext context) {
		CallScheduler scheduler = schedulers.get(schedulerId);
		if (null == scheduler) {
			logger.info("no next task found, call finish");
			return;
		}
		
		SwCommonCallSessionCreatePojo pojo = scheduler.popTask();
		if (null == pojo) {
			logger.info("the last task finished groupid: {}", schedulerId);
			schedulers.remove(schedulerId);
			return;
		}
		
		SwCallStartBean startBean = new SwCallStartBean(context, pojo);
		SwCallExecutorService.getExecutor().submit(startBean);
	}
}
