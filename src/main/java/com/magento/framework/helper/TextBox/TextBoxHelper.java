
package com.magento.framework.helper.TextBox;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magento.constants.Constants;
import com.magento.framework.helper.Generic.GenericHelper;
import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.helper.Wait.WaitHelper;
import com.magento.framework.settings.ObjectRepo;

/**
 * @author automation_user
 * 
 *
 */
public class TextBoxHelper extends GenericHelper {

	private WebDriver driver;
	private Logger oLog = LoggerHelper.getLogger(TextBoxHelper.class);
	private WaitHelper wHelper;

	public TextBoxHelper(WebDriver driver) {
		super(driver);
		this.driver = driver;
		oLog.debug("TextBoxHelper : " + this.driver.hashCode());
		wHelper = new WaitHelper(driver, ObjectRepo.reader);
	}

	public void sendKeys(By locator, String value) {
		oLog.info("Locator : " + locator + " Value : " + value);
		getElement(locator).sendKeys(value);
	}

	public void clear(By locator) {
		oLog.info("Locator : " + locator);
		getElement(locator).clear();
	}

	public String getText(By locator) {
		oLog.info("Locator : " + locator);
		return getElement(locator).getText();
	}

	public void clearAndSendKeys(By locator, String value) {
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
		oLog.info("Locator : " + locator + " Value : " + value);
	}
	
	public void clearAndSendKeys(WebElement element, String value) {
		element.clear();
		sendKeys(element, value);
		oLog.info("Element : " + element + " Value : " + value);
	}

	public String getText(WebElement element) {
//		wHelper.hardWait(2000);
		wHelper.waitForElementVisible(element, Constants.WAIT_EXPLICIT_SEC, Constants.WAIT_POLLING_MS);
//		wHelper.hardWait(2000);
		oLog.info("Locator : " + element);
		return element.getText();
	}

	public void sendKeys(WebElement locator, String value) {
		wHelper.waitForElementVisible(locator, Constants.WAIT_EXPLICIT_SEC, Constants.WAIT_POLLING_MS);
//		wHelper.hardWait(1000);
		oLog.info("Locator : " + locator + " Value : " + value);
		locator.sendKeys(value);
	}

}
