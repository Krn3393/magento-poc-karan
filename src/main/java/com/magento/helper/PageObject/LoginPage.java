package com.magento.helper.PageObject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.magento.framework.helper.BasePageObject.PageBase;
import com.magento.framework.helper.Button.ButtonHelper;
import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.helper.TextBox.TextBoxHelper;
import com.magento.framework.settings.ObjectRepo;

public class LoginPage extends PageBase {

	private WebDriver driver;

	private final static Logger log = LoggerHelper.getLogger(LoginPage.class);
	private ButtonHelper bHelper;
	private TextBoxHelper tHelper;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		bHelper = new ButtonHelper(driver);
		tHelper = new TextBoxHelper(driver);
	}

	/** Web Elements */

	@FindBy(id = "email")
	@CacheLookup
	private WebElement txtEmail;

	@FindBy(id = "pass")
	@CacheLookup
	private WebElement txtPassword;

	@FindBy(name = "send")
	@CacheLookup
	private WebElement btnLogin;

	@FindBy(xpath = "(//a[normalize-space()='Sign In'])[1]")
	WebElement lnkSignIn;

	/** Public Methods **/

	public WebDriver getDriver() {
		return this.driver;
	}

	public void enterUserName(String username) {
		try {
			tHelper.sendKeys(txtEmail, username);
			log.info(username);
		} catch (Exception e) {
			ObjectRepo.driver.findElement(By.id("username_new")).sendKeys(username);
		}
	}

	public void enterPass(String userpwd) {
		try {
			tHelper.sendKeys(txtPassword, userpwd);
			log.info(txtPassword);
		} catch (Exception e) {
			ObjectRepo.driver.findElement(By.id("password_new_show")).sendKeys(userpwd);
		}
	}

	public void clickOnLogInButton() {
		try {
			bHelper.click(btnLogin);
			log.info(btnLogin);
		} catch (Exception e) {
			ObjectRepo.driver.findElement(By.id("newLoginBtn")).click();
		}
	}

	public void clickOnSignInButton() {
		try {
			bHelper.click(lnkSignIn);
			log.info(btnLogin);
		} catch (Exception e) {
			ObjectRepo.driver.findElement(By.xpath("(//a[normalize-space()='Sign In'])[1]")).click();
		}
	}

}
