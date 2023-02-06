
package com.magento.framework.configuration.browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.magento.constants.Constants;
import com.magento.framework.utility.DateTimeHelper;
import com.magento.framework.utility.ResourceHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author automation_user
 *
 */
public class ChromeBrowser {

	public Capabilities getChromeCapabilities() {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("start-maximized");
		option.addArguments("--disable-notifications");
		option.addArguments("--disable-extenstions");
		option.addArguments("--disable-popup-blocking");
//		if (Constants.IS_HEADLESS) {
//			option.addArguments(new String[] { "headless" });
//			option.addArguments(new String[] { "window-size=1044x784" });
//		}
		if (Constants.IS_HEADLESS) {
			option.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
			option.addArguments("--headless", "window-size=1920,1080", "--no-sandbox");
			option.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
					"--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");

		}
		
		HashMap<String, Object> chromePref = new HashMap<>();
		chromePref.put("profile.default_content_settings.popups", 0);
		chromePref.put("download.default_directory", Constants.DOWNLOAD_DIR);
		option.setExperimentalOption("prefs", chromePref);
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setJavascriptEnabled(true);
		chrome.setCapability(ChromeOptions.CAPABILITY, option);
		return chrome;
	}

	public WebDriver getChromeDriver(Capabilities cap) {
//		System.setProperty("webdriver.chrome.driver", ResourceHelper.getResourcePath("drivers/chromedriver.exe"));
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.logfile", ResourceHelper.getResourcePath("logs/chromelogs/") + "chromelog"
				+ DateTimeHelper.getCurrentDateTime() + ".log");
		return new ChromeDriver(cap);
	}

	public WebDriver getChromeDriver(String hubUrl, Capabilities cap) throws MalformedURLException {
		return new RemoteWebDriver(new URL(hubUrl), cap);
	}

}
