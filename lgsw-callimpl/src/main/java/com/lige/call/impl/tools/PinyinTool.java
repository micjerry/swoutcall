package com.lige.call.impl.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTool {
	private static final Logger logger = LoggerFactory.getLogger(PinyinTool.class);

	private static HanyuPinyinOutputFormat format = null;

	static {
		format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	private static String getCharPinyin(char chi) {
		String[] pinyin = null;
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(chi, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error("convert to pinyin failed");
		}

		if (null == pinyin) {
			return null;
		}

		return pinyin[0];
	}

	public static String getPinyin(String ch) {
		StringBuffer sb = new StringBuffer();
		String tempStr = null;
		for (int i = 0; i < ch.length(); i++) {
			tempStr = PinyinTool.getCharPinyin(ch.charAt(i));
			if (tempStr == null) {
				sb.append(ch.charAt(i));
			} else {
				sb.append(tempStr);
			}
		}

		return sb.toString();

	}
}
