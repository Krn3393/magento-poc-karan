
package com.magento.framework.interfaces;

import com.magento.framework.configuration.browser.BrowserType;

public interface IconfigReader {
	public BrowserType getBrowser();

	public String getURL();

	public String getUserName();

	public String getPassword();

	public int getPageLoadTimeOut();

	public int getImplicitWait();

	public int getExplicitWait();

	public int getPollingTime();

}
