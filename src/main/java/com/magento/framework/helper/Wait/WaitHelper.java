
package com.magento.framework.helper.Wait;

import static com.magento.constants.Constants.WAIT_EXPLICIT_SEC;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.magento.constants.Constants;
import com.magento.framework.helper.Generic.GenericHelper;
import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.interfaces.IconfigReader;
import com.magento.framework.settings.ObjectRepo;

public class WaitHelper extends GenericHelper {

	private WebDriver driver;
	private IconfigReader reader;
	private Logger oLog = LoggerHelper.getLogger(WaitHelper.class);

	By loaderLocator = By.xpath("//div[@id='loading-text']");

	By delPopUpLocator = By.xpath("//div[@id='ifct-uc-medicalnote-deletebox']//div[@class='modal-content']");

	public WaitHelper(WebDriver driver, IconfigReader reader) {
		super(driver);
		this.driver = driver;
		this.reader = reader;
		oLog.debug("WaitHelper : " + this.driver.hashCode());
	}

	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
		oLog.debug("");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(pollingEveryInMiliSec, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	public void setImplicitWait(long timeout, TimeUnit unit) {
		oLog.info(timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit == null ? TimeUnit.SECONDS : unit);
	}

	public void waitForElementVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		oLog.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
		setImplicitWait(reader.getImplicitWait(), TimeUnit.SECONDS);
	}

	public void waitForElementVisible(WebElement welcomeScreenElement, int timeOutInSeconds,
			int pollingEveryInMiliSec) {
		oLog.info(welcomeScreenElement);
		do {
		} while (!waitForLoader());
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(welcomeScreenElement));
		setImplicitWait(reader.getImplicitWait(), TimeUnit.SECONDS);
	}

	public void hardWait(int timeOutInMiliSec) {
		oLog.info(timeOutInMiliSec);
		try {
			Thread.sleep(timeOutInMiliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebElement handleStaleElement(By locator, int retryCount, int delayInSeconds) throws InterruptedException {
		oLog.info(locator);
		WebElement element = null;

		while (retryCount >= 0) {
			try {
				element = driver.findElement(locator);
				System.out.println("element found");
				return element;
			} catch (StaleElementReferenceException e) {
				System.out.println("stale exception");
				hardWait(delayInSeconds);
				retryCount--;
			}
		}
		throw new StaleElementReferenceException("Element cannot be recovered");
	}

	public void elementExits(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		oLog.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(elementLocatedBy(locator));
		setImplicitWait(reader.getImplicitWait(), TimeUnit.SECONDS);
	}

	public void elementExistAndVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		oLog.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(elementLocatedBy(locator));
//		new JavaScriptHelper(driver).scrollIntoView(locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		setImplicitWait(reader.getImplicitWait(), TimeUnit.SECONDS);

	}

	public void waitForIframe(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		oLog.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		driver.switchTo().defaultContent();
		setImplicitWait(reader.getImplicitWait(), TimeUnit.SECONDS);
	}

	private Function<WebDriver, Boolean> elementLocatedBy(final By locator) {
		return new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				oLog.debug(locator);
				return driver.findElements(locator).size() >= 1;
			}
		};
	}

	public void waitForVisbleOfElement(WebElement webElement) {
//		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);

	}

	public void waitForElementToBeClickable(WebElement webElement) {
//		do {
//		} while (!waitForLoader());
		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void waitForElementToBeClickable(By One, WebElement Two) {

		System.out.println("waitForElementToBeClickable");
		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);
		wait.until(ExpectedConditions.elementToBeClickable(Two));

		boolean invisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(null)));
		if (invisible) {

			Two.click();

		}

	}

	public void waitForElementToBeClickableAndClick(WebElement element) {

		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);
		// Wait till "Cancel" button is showing up. At cases, it may take some time.
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(element));
		((JavascriptExecutor) ObjectRepo.driver).executeScript("arguments[0].click()", el);

	}

	public void waitForElementToBeClickable(By Xpath) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_EXPLICIT_SEC);
		wait.until(ExpectedConditions.elementToBeClickable(Xpath));
	}

	public void waitForPresenceOfElement(By Xpath) {
		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);
		wait.until(ExpectedConditions.presenceOfElementLocated(Xpath));
	}

	public void waitForVisibility(WebElement element) {
		WebDriverWait wait = new WebDriverWait(ObjectRepo.driver, WAIT_EXPLICIT_SEC);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForVisibilityLoader() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5000);
			WebElement webElement = driver.findElement(loaderLocator);

			wait.until(ExpectedConditions.visibilityOf(webElement)); // wait for loader to appear
			wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator)); // wait for loader to disappear
		} catch (org.openqa.selenium.TimeoutException e) {
			// System.out.println("timeout exxception catch");
		} catch (org.openqa.selenium.StaleElementReferenceException e1) {
			// System.out.println("beforevisibility loader catch");
			handleStaleElement(loaderLocator, 5, 5);

			waitForVisibilityLoader();

		} catch (org.openqa.selenium.NoSuchElementException e) {
			// System.out.println("no such catch");
			waitForVisibilityLoader();
		}

	}

	public void waitForDeletePopup() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5000);
			System.out.println("beforevisibility pop-up");

			WebElement webElement = driver.findElement(delPopUpLocator);

			wait.until(ExpectedConditions.visibilityOf(webElement)); // wait for loader to appear
			wait.until(ExpectedConditions.invisibilityOfElementLocated(delPopUpLocator)); // wait for loader to
																							// disappear
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("timeout exxception catch");
		} catch (org.openqa.selenium.StaleElementReferenceException e1) {
			System.out.println("beforevisibility pop-up catch");
			// handleStaleElement(loaderLocator,5,5);

			// waitForVisibilityLoader();

		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("no such catch");
			// waitForVisibilityLoader();
		}

	}

	public void clickJava(By xpaths) {

		/*
		 * WebDriver driver = new AnyDriverYouWant(); if (driver instanceof
		 * JavascriptExecutor) {
		 * ((JavascriptExecutor)driver).executeScript("yourScript();"); } else { throw
		 * new IllegalStateException("This driver does not support JavaScript!"); }
		 */

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", xpaths);

	}

	public boolean waitForLoader() {
		int reloadCounter = 0;
		hardWait(900);
		if (isLoderDisplayed(By.xpath("//*[contains(@class,'loader_loaderContainer')]"))) {
			Instant currentTime = getCurrentTime();
			while (isLoderDisplayed(By.xpath("//*[contains(@class,'loader_loaderContainer')]"))) {
				Instant loopingTime = getCurrentTime();
				Duration timeElapsed = Duration.between(currentTime, loopingTime);
				long sec = timeElapsed.toMillis() / 1000;
				int durDiff = (int) sec;
				if (durDiff >= Constants.LOADER_WAIT) {
//					reloadCurrentPage();
					reloadCounter++;
					if (reloadCounter == 2)
						assertTrue(false, "Continuous Loader Displaying");
				}
			}
		}
		return true;
	}

	private boolean isLoderDisplayed(By xpath) {
		boolean state = false;
		try {
			state = driver.findElement(xpath).isDisplayed();
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	private Instant getCurrentTime() {
		return Instant.now();
	}
}
