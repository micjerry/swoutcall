package com.lige.call.impl.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.beans.SwCallExecutorFactoryImpl;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerHangup;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerManager;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerTransfer;
import com.lige.call.impl.switcheventhandlers.SwEventChannelAnswerHandler;
import com.lige.call.impl.switcheventhandlers.SwEventChannelCreateHandler;
import com.lige.call.impl.switcheventhandlers.SwEventChannelDestroyHandler;
import com.lige.call.impl.switcheventhandlers.SwEventDetectSpeechHandler;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerManager;

@Configuration
public class SwCallAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SwCallExecutorFactory getConfControlFactory() {
		return new SwCallExecutorFactoryImpl();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwCallOptHandlerManager getOperHandlerManager() {
		return new SwCallOptHandlerManager();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerManager getSwitchEventHandlerManager() {
		return new SwEventHandlerManager();
	}
	
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventChannelAnswerHandler getNtyHandlerAddMember() {
		return new SwEventChannelAnswerHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	SwEventChannelCreateHandler getNtyHandlerChannelDestroy() {
		return  new SwEventChannelCreateHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventChannelDestroyHandler getNtyHandlerCreateConfer() {
		return new SwEventChannelDestroyHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventDetectSpeechHandler getNtyHandlerConferDestroy() {
		return new SwEventDetectSpeechHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public SwCallOptHandlerHangup getOptHangupHandler() {
		return new SwCallOptHandlerHangup();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwCallOptHandlerTransfer getOptTransferHandler() {
		return new SwCallOptHandlerTransfer();
	}
	
	
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(15000);
		return factory;
	}

}
