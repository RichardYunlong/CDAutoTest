package Plugins;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static final Logger log = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);
	private static final String PREFIX = "Encrypt:";

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
