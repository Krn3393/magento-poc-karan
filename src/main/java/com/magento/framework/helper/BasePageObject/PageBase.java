
package com.magento.framework.helper.BasePageObject;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;
import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.settings.ObjectRepo;

@SuppressWarnings("rawtypes")
public abstract class PageBase {

	private final Logger log = LoggerHelper.getLogger(PageBase.class);

	private WebDriver driver;

	@FindBy(xpath = "//input[@id='email']")
	@CacheLookup
	private WebElement emailAddress;

	@FindBy(xpath = "//input[@id='password']")
	@CacheLookup
	private WebElement password;

	@FindBy(xpath = "//button[@id='next']")
	@CacheLookup
	private WebElement signInButton;

	private By getFindByAnno(FindBy anno) {
		log.info(anno);
		switch (anno.how()) {

		case CLASS_NAME:
			return new By.ByClassName(anno.using());
		case CSS:
			return new By.ByCssSelector(anno.using());
		case ID:
			return new By.ById(anno.using());
		case LINK_TEXT:
			return new By.ByLinkText(anno.using());
		case NAME:
			return new By.ByName(anno.using());
		case PARTIAL_LINK_TEXT:
			return new By.ByPartialLinkText(anno.using());
		case XPATH:
			return new By.ByXPath(anno.using());
		default:
			throw new IllegalArgumentException("Locator not Found : " + anno.how() + " : " + anno.using());
		}
	}

	protected By getElemetLocator(Object obj, String element) throws SecurityException, NoSuchFieldException {
		Class childClass = obj.getClass();
		By locator = null;
		try {
			locator = getFindByAnno(childClass.getDeclaredField(element).getAnnotation(FindBy.class));
		} catch (SecurityException | NoSuchFieldException e) {
			log.equals(e);
			throw e;
		}
		log.debug(locator);
		return locator;
	}

	public void waitForElement(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(ElementNotFoundException.class);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.until(elementLocated(element));
	}

	private Function<WebDriver, Boolean> elementLocated(final WebElement element) {
		return new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				log.debug("Waiting for Element : " + element);
				return element.isDisplayed();
			}
		};
	}

	public PageBase(WebDriver driver) {
		if (driver == null)
			throw new IllegalArgumentException("Driver object is null");

		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
		this.driver = driver;
	}

	public boolean checkForTitle(String title) {
		log.info(title);
		if (title == null || title.isEmpty())
			throw new IllegalArgumentException(title);
		return driver.getTitle().trim().contains(title);
	}

	public void login() {
		ObjectRepo.driver.get(ObjectRepo.reader.getURL());
		enterUserName(ObjectRepo.reader.getUserName());
		enterPass(ObjectRepo.reader.getPassword());
		clickOnLogInButton();
	}

	public void enterUserName(String username) {
		emailAddress.sendKeys(username);
		log.info(username);
	}

	public void enterPass(String userpwd) {
		password.sendKeys(userpwd);
		log.info(password);
	}

	public void clickOnLogInButton() {
		signInButton.click();
		log.info(signInButton);
	}

}
