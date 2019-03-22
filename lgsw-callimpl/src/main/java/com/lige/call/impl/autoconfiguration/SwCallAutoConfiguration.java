package com.lige.call.impl.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.lige.call.api.exe.SwCallExecutorFactory;
import com.lige.call.impl.beans.SwCallExecutorFactoryImpl;
import com.lige.call.impl.operatehandlers.OptHandlerFactory;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerHangup;
import com.lige.call.impl.operatehandlers.SwCallOptHandlerTransfer;
import com.lige.call.impl.switcheventhandlers.EventHandlerFactory;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerChannelAnswer;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerChannelCreate;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerChannelDestroy;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerDetectSpeech;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerPlayStart;
import com.lige.call.impl.switcheventhandlers.SwEventHandlerPlayStop;

@Configuration
public class SwCallAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SwCallExecutorFactory getCallExecutorFactory() {
		return new SwCallExecutorFactoryImpl();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public OptHandlerFactory getOperHandleFactory() {
		OptHandlerFactory optHandlerFactory = new OptHandlerFactory();
		optHandlerFactory.initialHandlers();
		return optHandlerFactory;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public EventHandlerFactory getSwitchEventHandlerFactory() {
		EventHandlerFactory eventHanderFactory =  new EventHandlerFactory();
		eventHanderFactory.initialHandlers();
		return eventHanderFactory;
	}

	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerChannelAnswer getEventHandlerChannelAnswer() {
		return new SwEventHandlerChannelAnswer();
	}
	
	@Bean
	@ConditionalOnMissingBean	
	public SwEventHandlerChannelCreate getEventHandlerChannelCreate(){
		return new SwEventHandlerChannelCreate();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerChannelDestroy getEventHandlerChannelDestroy() {
		return new SwEventHandlerChannelDestroy();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerDetectSpeech getEventHandlerDetectSpeech() {
		return new SwEventHandlerDetectSpeech();
	}

	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerPlayStart getEventHandlerPlayStart() {
		return new SwEventHandlerPlayStart();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SwEventHandlerPlayStop getEventHandlerPlayStop() {
		return new SwEventHandlerPlayStop();
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
