
package com.magento.framework.helper.Generic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.interfaces.IwebComponent;
import com.magento.framework.settings.ObjectRepo;
import com.magento.framework.utility.DateTimeHelper;
import com.magento.framework.utility.ResourceHelper;

/**
 * @author automation_user
 *
 *
 */
public class GenericHelper implements IwebComponent {

	private WebDriver driver;
	private Logger oLog = LoggerHelper.getLogger(GenericHelper.class);

	public GenericHelper(WebDriver driver) {
		this.driver = driver;
		oLog.debug("GenericHelper : " + this.driver.hashCode());
	}

	public WebElement getElement(By locator) {
		oLog.info(locator);
		if (IsElementPresentQuick(locator))
			return driver.findElement(locator);

		try {
			throw new NoSuchElementException("Element Not Found : " + locator);
		} catch (RuntimeException re) {
			oLog.error(re);
			throw re;
		}
	}

	public List<WebElement> getElements(By locator) {
		oLog.info(locator);
		return driver.findElements(locator);
	}

	/**
	 * Check for element is present based on locator If the element is present
	 * return the web element otherwise null
	 * 
	 * @param locator
	 * @return WebElement or null
	 */

	public WebElement getElementWithNull(By locator) {
		oLog.info(locator);
		try {
			return driver.findElement(locator);
		} catch (NoSuchElementException e) {
			// Ignore
		}
		return null;
	}

	public boolean IsElementPresentQuick(By locator) {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
		boolean flag = driver.findElements(locator).size() >= 1;
		oLog.info(flag);
		return flag;
	}

	public String takeScreenShot(String name) throws IOException {
		if (driver instanceof HtmlUnitDriver) {
			oLog.fatal("HtmlUnitDriver Cannot take the ScreenShot");
			return "";
		}

		File destDir = new File(ResourceHelper.getResourcePath("screenshots/") + DateTimeHelper.getCurrentDate());
		if (!destDir.exists())
			destDir.mkdir();

		File destPath = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + name + ".jpg");
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destPath);
		} catch (IOException e) {
			oLog.error(e);
			throw e;
		}
		oLog.info(destPath.getAbsolutePath());
		return destPath.getAbsolutePath();
	}

	public String takeScreenShotSuccess(String name) throws IOException {
		if (driver instanceof HtmlUnitDriver) {
			oLog.fatal("HtmlUnitDriver Cannot take the ScreenShot");
			return "";
		}
		File destDir = new File("Success_Screenshot");
		if (!destDir.exists())
			destDir.mkdir();

		File destPath = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + name + ".jpg");
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destPath);
		} catch (IOException e) {
			oLog.error(e);
			throw e;
		}
		oLog.info(destPath.getAbsolutePath());
		return destPath.getAbsolutePath();
	}

	public String takeScreenShot() {
		oLog.info("");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

	public void enterKeys(Keys keys) {
		Actions action = new Actions(ObjectRepo.driver);
		action.sendKeys(keys).build().perform();
	}

	public void focusOnElement(WebElement element) {
		new Actions(ObjectRepo.driver).moveToElement(element).build().perform();
	}

	public void reloadPage() {
		ObjectRepo.driver.navigate().refresh();
		acceptAlert();
		acceptAlert();
		acceptAlert();
		acceptAlert();
		acceptAlert();
	}

	public void acceptAlert() {
		try {
			Thread.sleep(2000);
			ObjectRepo.driver.switchTo().alert().accept();
		} catch (Exception e) {
		}
	}

	public String getAlert() {
		try {
			return ObjectRepo.driver.switchTo().alert().getText();
		} catch (Exception e) {
		}
		return "";
	}

	public void handleDownloadBar() {
		try {
			Thread.sleep(5000);
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
			robot.keyPress(java.awt.event.KeyEvent.VK_J);
			Thread.sleep(2000);
			robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
			robot.keyRelease(java.awt.event.KeyEvent.VK_J);
			Thread.sleep(500);
			ArrayList<String> tabs2 = new ArrayList<String>(ObjectRepo.driver.getWindowHandles());
			ObjectRepo.driver.switchTo().window(tabs2.get(1));
			Thread.sleep(500);
			ObjectRepo.driver.close();
			ObjectRepo.driver.switchTo().window(tabs2.get(0));
			Thread.sleep(500);
		} catch (Exception e) {
		}
	}
}
