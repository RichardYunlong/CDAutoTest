package Plugins;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ConfigPropertiesFactory extends PropertiesFactoryBean {
	public void setLocation(Resource location) {
		super.setLocation(new FileSystemResource(CommonUtil.getConfigPath() + location.getFilename()));
	}
}
