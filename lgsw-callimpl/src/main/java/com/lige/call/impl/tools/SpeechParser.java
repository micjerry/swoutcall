package com.lige.call.impl.tools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SpeechParser {
	private static final Logger logger = LoggerFactory.getLogger(SpeechParser.class);
	
	public static DecodeSpeechDetectResult parseSpeech(List<String> speech) {
		if (null == speech) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder(200);
		for (String line: speech) {
			sb.append(line);
		}
		
		XmlMapper xmlMapper = new XmlMapper();
		DecodeSpeechDetectResult result = null;
		try {
    		result = xmlMapper.readValue(sb.toString(), DecodeSpeechDetectResult.class);
    	} catch(Exception es) {
    		logger.error("invalid detect result received {}", sb.toString());
    		return null;
    	}
		
		return result;
	}

}
