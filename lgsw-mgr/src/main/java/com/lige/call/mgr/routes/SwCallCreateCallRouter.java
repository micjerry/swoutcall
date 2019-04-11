package com.lige.call.mgr.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lige.call.api.exe.SwCallExecutor;
import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.mgr.beans.SwCallHttpResponseBean;
import com.lige.call.mgr.config.RabbitmqConfig;
import com.lige.common.call.api.oper.SwCommonCallDialogNode;
import com.lige.common.call.api.oper.SwCommonCallSessionCreatePojo;

@Component
public class SwCallCreateCallRouter extends RouteBuilder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@Autowired
	private CamelContext context;

	@Autowired
	private SwCallExecutorFactory factory;

	@Autowired
	private RabbitmqConfig config;

	@Autowired
	private SwCallHttpResponseBean responseHandler;

	@Value("${outcall.host}")
	private String host;

	@Value("${outcall.port}")
	private String port;

	@Override
	public void configure() throws Exception {
		StringBuilder fromUrl = new StringBuilder("netty-http:http://");
		fromUrl.append(host);
		fromUrl.append(":");
		fromUrl.append(port);
		fromUrl.append("/swcall/createcall");
		from(fromUrl.toString()).unmarshal().json(JsonLibrary.Jackson, SwCommonCallSessionCreatePojo.class)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						Message message = exchange.getIn();
						SwCommonCallSessionCreatePojo body = message.getBody(SwCommonCallSessionCreatePojo.class);

						if (!checkReq(body)) {
							return;
						}

						SwCallExecutor executor = factory.create(body);

						if (executor == null) {
							logger.error(
									"Create call failed" + exchange.getIn().getHeader("CamelHttpQuery", String.class));
							return;
						}

						logger.debug("create new callexecutor {}", executor.toString());

						SwCallExecutorRouter callctrl = new SwCallExecutorRouter(body.getTaskid(), executor, config,
								body.getSwid(), responseHandler);
						context.addRoutes(callctrl);
					}
				});

	}

	private boolean checkReq(SwCommonCallSessionCreatePojo body) {
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
		
		body.setCalleenumber(formatNumber(body.getCalleenumber()));

		if (null == body.getCalleenumber() || "".equals(body.getCalleenumber())) {
			logger.error("invalid req no callee number");
			return false;
		}

		if (null == body.getSwid() || "".equals(body.getSwid())) {
			logger.error("invalid req no swid");
			return false;
		}

		if (null == body.getDialog() || null == body.getDialog().getNodes() || body.getDialog().getNodes().isEmpty()) {
			logger.error("invalid req dialog");
			return false;
		}

		boolean hasFirst = false;
		for (SwCommonCallDialogNode node : body.getDialog().getNodes()) {
			if (null == node.getPlay() || "".equals(node.getPlay())) {
				logger.error("invalid node: {}", node.getName());
				return false;
			}

			if (node.isFirst())
				hasFirst = true;
		}

		if (!hasFirst) {
			logger.warn("no first node was set dialog {}", body.getDialog().getName());
		}

		return true;
	}

	private String formatNumber(String number) {
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

		for (String prefix: CHINA_MOBILE_PREFIXS) {
			if (formatedNumber.startsWith(prefix)) {
				logger.info("it is a mobile number: {}", formatedNumber);
				return formatedNumber;
			}
		}
		
		for (String prefix: CHINA_TELECOM_PREFIXS) {
			if (formatedNumber.startsWith(prefix)) {
				logger.info("it is a telecom number: {}", formatedNumber);
				return formatedNumber;
			}
		}
		
		for (String prefix: CHINA_UNICOM_PREFIXS) {
			if (formatedNumber.startsWith(prefix)) {
				logger.info("it is a unicom number: {}", formatedNumber);
				return formatedNumber;
			}
		}
		
		logger.error("invalid number: {} unknown telecom company", formatedNumber);
		
		return null;

	}
}
