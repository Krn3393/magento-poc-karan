package com.magento.framework.configreader;

import java.util.Properties;

import com.magento.framework.interfaces.IconfigReader;
import com.magento.framework.utility.ResourceHelper;

public class PropertyFileReader implements IconfigReader {

	private Properties prop = null;

	public PropertyFileReader() {
		prop = new Properties();
		try {
			prop.load(ResourceHelper.getResourcePathInputStream("configfile/" + "config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public com.magento.framework.configuration.browser.BrowserType getBrowser() {
		return com.magento.framework.configuration.browser.BrowserType.valueOf(prop.getProperty("Browser"));
	}

	public String getWaitTime() {
		String time = prop.getProperty("wait.explicit.seconds");
		if (time != null)
			return time;
		else
			throw new RuntimeException("Time  is  not specified in the Configuration.properties file.");

	}

	@Override
	public String getURL() {
		return prop.getProperty("URL");
	}

	@Override
	public String getUserName() {
		return prop.getProperty("UserName");
	}

	@Override
	public String getPassword() {
		return prop.getProperty("Password");
	}

	@Override
	public int getPageLoadTimeOut() {
		return Integer.parseInt(prop.getProperty("PageLoadTimeOut"));
	}

	@Override
	public int getImplicitWait() {
		return Integer.parseInt(prop.getProperty("ImplicitWait"));
	}

	@Override
	public int getExplicitWait() {
		return Integer.parseInt(prop.getProperty("ExplicitWait"));
	}

	@Override
	public int getPollingTime() {
		return Integer.parseInt(prop.getProperty("PollingTime"));
	}

}
