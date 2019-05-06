package com.lige.call.mgr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lige.common.call.api.oper.SwCommonCallDialog;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallSessionCreateMultiPojo;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

public class ReqCheckUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReqCheckUtil.class);
	
	private static final String CHINA_PREFIX1 = "0086";

	private static final String CHINA_PREFIX2 = "+86";

	private static final int CHINA_MOBILE_NUBLEN = 11;

	private static final String[] CHINA_MOBILE_PREFIXS = { "1340", "1341", "1342", "1343", "1344", "1345", "1346",
			"1347", "1348", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "178",
			"182", "183", "184", "187", "188", "198", "1703", "1705", "1706" };

	private static final String[] CHINA_TELECOM_PREFIXS = { "133", "153", "180", "181", "189", "173", "177", "199",
			"1700", "1701", "1702" };

	private static final String[] CHINA_UNICOM_PREFIXS = { "130", "131", "132", "145", "155", "156", "166", "175",
			"176", "185", "186", "1704", "1707", "1708", "1709", "171" };
	
	private static final String CHINA_MOBILE_NAME = "mobile";
	
	private static final String CHINA_TELECOM_NAME = "telecom";
	
	private static final String CHINA_UNICOM_NAME = "unicom";
	
	public static String formatNumber(String number) {
		String formatedNumber;
		if (number.startsWith(CHINA_PREFIX1)) {
			formatedNumber = number.substring(CHINA_PREFIX1.length());
		} else if (number.startsWith(CHINA_PREFIX2)) {
			formatedNumber = number.substring(CHINA_PREFIX2.length());
		} else {
			formatedNumber = number;
		}

		if (formatedNumber.length() != CHINA_MOBILE_NUBLEN) {
			logger.error("invalid number: {}", formatedNumber);
			return null;
		}
		
		if (checkTele(formatedNumber, CHINA_MOBILE_PREFIXS, CHINA_MOBILE_NAME)) {
			return formatedNumber;
		}
		
		if (checkTele(formatedNumber, CHINA_TELECOM_PREFIXS, CHINA_TELECOM_NAME)) {
			return formatedNumber;
		}
		
		if (checkTele(formatedNumber, CHINA_UNICOM_PREFIXS, CHINA_UNICOM_NAME)) {
			return formatedNumber;
		}
		

		logger.error("invalid number: {} unknown telecom company", formatedNumber);
		
		return null;
	}
	
	private static boolean checkTele(String number, String[] prefixs, String teleName) {
		for (String prefix: prefixs) {
			if (number.startsWith(prefix)) {
				logger.info("it is a {} number: {}", teleName, number);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkReq(SwCommonCallSessionCreatePojo body) {
		if (null == body.getTaskid() || "".equals(body.getTaskid())) {
			logger.error("invalid req no id");
			return false;
		}

		if (null == body.getUserid() || "".equals(body.getUserid())) {
			logger.error("invalid req no user id");
			return false;
		}

		if (null == body.getRobotid() || "".equals(body.getRobotid())) {
			logger.error("invalid req no robot id");
			return false;
		}
		
		body.setCalleenumber(ReqCheckUtil.formatNumber(body.getCalleenumber()));

		if (null == body.getCalleenumber() || "".equals(body.getCalleenumber())) {
			logger.error("invalid req no callee number");
			return false;
		}

		if (null == body.getSwid() || "".equals(body.getSwid())) {
			logger.error("invalid req no swid");
			return false;
		}
		
		SwCommonCallDialog dialog = body.getDialog();

		if (null == dialog || null == dialog.getNodes() || dialog.getNodes().isEmpty()) {
			logger.error("invalid req dialog");
			return false;
		}
		
		if (null == dialog.getId() || "".equals(dialog.getId())) {
			logger.error("invalid req no dialog id");
			return false;
		}

		boolean hasFirst = false;
		for (SwCommonCallDialogNode node : dialog.getNodes()) {
			if (null == node.getPlay() || "".equals(node.getPlay())) {
				logger.error("invalid node: {}", node.getName());
				return false;
			}

			if (node.isFirst())
				hasFirst = true;
		}

		if (!hasFirst) {
			logger.warn("no first node was set dialog {}", dialog.getName());
		}

		return true;
	}
	
	public static SwCommonCallSessionCreatePojo makeSession(SwCommonCallSessionCreateMultiPojo multiSession) {
		if (null == multiSession)
			return null;
		
		SwCommonCallSessionCreatePojo pojo = new SwCommonCallSessionCreatePojo();
		
		pojo.setGroupid(multiSession.getGroupid());
		pojo.setTaskid(multiSession.getTaskid());
		pojo.setUserid(multiSession.getUserid());
		pojo.setRobotid(multiSession.getRobotid());
		pojo.setCallernumber(multiSession.getCallernumber());
		pojo.setGateway(multiSession.getGateway());
		pojo.setSwid(multiSession.getSwid());
		pojo.setMaxduration(multiSession.getMaxduration());
		pojo.setRingduration(multiSession.getRingduration());
		pojo.setDialog(multiSession.getDialog());
		
		return pojo;
	}
}
