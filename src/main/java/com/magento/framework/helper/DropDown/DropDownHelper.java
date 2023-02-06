
package com.magento.framework.helper.DropDown;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.magento.framework.helper.Generic.GenericHelper;
import com.magento.framework.helper.Logger.LoggerHelper;

/**
 * @author automation_user
 * 
 *
 */
public class DropDownHelper extends GenericHelper {

	private WebDriver driver;
	private Logger oLog = LoggerHelper.getLogger(DropDownHelper.class);

	public DropDownHelper(WebDriver driver) {
		super(driver);
		this.driver = driver;
		oLog.debug("DropDownHelper : " + this.driver.hashCode());
	}

	public void SelectUsingVisibleValue(By locator, String visibleValue) {
		SelectUsingVisibleValue(getElement(locator), visibleValue);
	}

	public void SelectUsingVisibleValue(WebElement element, String visibleValue) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleValue);
		oLog.info("Locator : " + element + " Value : " + visibleValue);
	}

	public void SelectUsingValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
		oLog.info("Locator : " + locator + " Value : " + value);
	}

	public void SelectUsingIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
		oLog.info("Locator : " + locator + " Index : " + index);
	}

	public String getSelectedValue(By locator) {
		oLog.info(locator);
		return getSelectedValue(getElement(locator));
	}

	public String getSelectedValue(WebElement element) {
		String value = new Select(element).getFirstSelectedOption().getText();
		oLog.info("WebELement : " + element + " Value : " + value);
		return value;
	}

	public List<String> getAllDropDownValues(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> elementList = select.getOptions();
		List<String> valueList = new LinkedList<String>();

		for (WebElement element : elementList) {
			oLog.info(element.getText());
			valueList.add(element.getText());
		}
		return valueList;
	}

	public void selectDataFromListOfDRP(By List, String Text, By dropdwn) {

		System.out.println("Inside selectSource method");
		System.out.println(List + "LIST");
		System.out.println(dropdwn + "DROPDOWN");

		System.out.println(driver);

		driver.findElement(dropdwn).click();

		List<WebElement> list = driver.findElements(List);

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getText().contains(Text)) {

				list.get(i).click();

				break;

			}

		}

	}

}
