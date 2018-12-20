package Plugins;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 *  配置文件驱动
 */
public class ConfigPropertiesFactory extends PropertiesFactoryBean {
	/**
	 *  设置配置文件来源
	 */
	public void setLocation(Resource location) {
		super.setLocation(new FileSystemResource(CommonUtil.getConfigPath() + location.getFilename()));
	}
}
