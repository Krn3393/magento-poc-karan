
package com.magento.framework.helper;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.magento.constants.Constants;
import com.magento.framework.configreader.PropertyFileReader;
import com.magento.framework.configuration.browser.BrowserType;
import com.magento.framework.configuration.browser.ChromeBrowser;
import com.magento.framework.helper.Generic.GenericHelper;
import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.settings.ObjectRepo;
import com.magento.helper.PageObject.LoginPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class InitializeWebDriver {

	private Logger oLog = LoggerHelper.getLogger(InitializeWebDriver.class);
//	private Logger LOGGER = LogManager.getLogger(InitializeWebDriver.class);
//	private static ExtentTest test;
//	private static boolean flag;
	public static Scenario scenario;

	public InitializeWebDriver(PropertyFileReader reader) {
		ObjectRepo.reader = reader;
	}

	@SuppressWarnings("deprecation")
	public WebDriver standAloneStepUp(BrowserType bType) throws Exception {
		try {
			oLog.info(bType);
			ChromeBrowser chrome = ChromeBrowser.class.newInstance();
			return chrome.getChromeDriver(chrome.getChromeCapabilities());
		} catch (Exception e) {
			oLog.equals(e);
			throw e;
		}
	}

	@Before()
	public void before(Scenario scenario) throws Exception {
		setUpDriver(scenario, ObjectRepo.reader.getBrowser());
		oLog.info(ObjectRepo.reader.getBrowser());
	}

	@After()
	public void after(Scenario scenario) throws Exception {
		tearDownDriver(scenario);
		oLog.info("");
	}

	@Before(order = 2, value = { "@chrome" })
	public void beforeChrome(Scenario scenario) throws Exception {
		setUpDriver(scenario, BrowserType.Chrome);
		oLog.info(BrowserType.Chrome);
	}

	@After(order = 2, value = { "@chrome" })
	public void afterChrome(Scenario scenario) throws Exception {
		tearDownDriver(scenario);
		oLog.info("");
	}

	public void setUpDriver(Scenario scenario, BrowserType bType) throws Exception {
		InitializeWebDriver.scenario = scenario;
//		flag = false;
//		String scenarioName = scenario.getName();
//		test = TestRunner
		if (!Boolean.parseBoolean(System.getProperty("API"))) {
			if (Constants.loginCounter == 0) {
				ObjectRepo.driver = standAloneStepUp(bType);
				oLog.debug("InitializeWebDrive : " + ObjectRepo.driver.hashCode());
				ObjectRepo.driver.manage().timeouts().pageLoadTimeout(ObjectRepo.reader.getPageLoadTimeOut(),
						TimeUnit.SECONDS);
				ObjectRepo.driver.manage().timeouts().implicitlyWait(ObjectRepo.reader.getImplicitWait(),
						TimeUnit.SECONDS);
				ObjectRepo.driver.manage().window().maximize();
				ObjectRepo.driver.get(ObjectRepo.reader.getURL());
				LoginPage lP = new LoginPage(ObjectRepo.driver);
				lP.clickOnSignInButton();
				lP.enterUserName(ObjectRepo.reader.getUserName());
				lP.enterPass(ObjectRepo.reader.getPassword());
				lP.clickOnLogInButton();
				Constants.loginCounter++;
			}
		}
	}

	public void tearDownDriver(Scenario scenario) throws Exception {
		try {
			if (ObjectRepo.driver != null) {
				if (scenario.isFailed())
					scenario.write(new GenericHelper(ObjectRepo.driver).takeScreenShot(scenario.getName()));
//				ObjectRepo.driver.quit();
//				ObjectRepo.reader = null;
//				ObjectRepo.driver = null;
//				oLog.info("Shutting Down the driver");
			}
		} catch (Exception e) {
			oLog.error(e);
			throw e;
		}
	}
}
