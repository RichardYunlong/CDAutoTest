package Plugins;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *  加密处理
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	/**
	 *  获取日志
	 */
	private static final Logger log = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);
	
	/**
	 *  加密信息头
	 */
	private static final String PREFIX = "Encrypt:";

	/**
	 *  读取并解密属性值
	 */
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String placeholderValue = props.getProperty(placeholder);
		if (placeholderValue.indexOf(PREFIX) != -1) {
			placeholderValue = placeholderValue.substring(PREFIX.length());
			try {
				placeholderValue = CommonUtil.decodeStr(placeholderValue);
				log.debug(placeholder + ": " + placeholderValue);
			} catch (Exception e) {
				log.error("resolvePlaceholder error", e);
			}
		}
		return placeholderValue;
	}
}
