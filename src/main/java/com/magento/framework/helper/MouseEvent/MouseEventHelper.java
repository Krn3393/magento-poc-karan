
package com.magento.framework.helper.MouseEvent;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.helper.Wait.WaitHelper;
import com.magento.framework.interfaces.IwebComponent;
import com.magento.framework.settings.ObjectRepo;

/**
 * @author automation_user
 *
 */
public class MouseEventHelper implements IwebComponent {

	private WebDriver driver;
	private Logger oLog = LoggerHelper.getLogger(MouseEventHelper.class);
	private WaitHelper wHelper;

	public MouseEventHelper(WebDriver driver) {
		this.driver = driver;
		oLog.debug("Button Helper : " + this.driver.hashCode());
		wHelper = new WaitHelper(ObjectRepo.driver, ObjectRepo.reader);
	}

	public void moveToElement(By locator) {
		wHelper.waitForElementToBeClickable(locator);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).perform();
		oLog.info(locator);
	}

}
