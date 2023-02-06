package com.magento.framework.stepdefinition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import com.magento.constants.DataConstants;
import com.magento.framework.helper.InitializeWebDriver;
import com.magento.framework.settings.ObjectRepo;
import com.magento.helper.PageObject.OrderPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateOrderDef {

	public OrderPage oP = new OrderPage(ObjectRepo.driver);

	@Given("^user search \"([^\"]*)\" in \"([^\"]*)\" for \"([^\"]*)\"$")
	public void user_search_in_for(String arg1, String arg2, String arg3) throws Throwable {
		oP.navigateToProductList(arg1, arg2, arg3);
	}

	@And("^user select size and color$")
	public void user_select_size_and_color() throws Throwable {
		oP.selectSize();
		oP.selectColor();
	}

	@And("^user select quantity \"([^\"]*)\"$")
	public void user_select_quantity(String arg1) throws Throwable {
		oP.enterQty(arg1);
	}

	@And("^user add the product to cart$")
	public void user_add_the_product_to_cart() throws Throwable {
		oP.clickOnAddToCart();
	}

	@Then("^user is able to see resulted products$")
	public void user_is_able_to_see_resulted_products() throws Throwable {
		Assert.assertTrue(oP.verifyProductsAreDisplayes());
	}

	@When("^user select first product$")
	public void user_select_first_product() throws Throwable {
		oP.selectFirstProduct();
	}

	@Then("^user can able to see cart value updated$")
	public void user_can_able_to_see_cart_value_updated() throws Throwable {
		Assert.assertTrue(oP.verifyCartUpdated());
	}

	@Given("^user navigate to cart$")
	public void user_navigate_to_cart() throws Throwable {
		oP.clickOnViewCart();
	}

	@Then("^user can able to fetch total cart price$")
	public void user_can_able_to_fetch_total_cart_price() throws Throwable {
		DataConstants.cart_price = oP.fetchPriceOnCart();
	}

	@Then("^user go to checkout page$")
	public void user_go_to_checkout_page() throws Throwable {
		oP.clickOnCheckout();
	}

	@When("^user add new address$")
	public void user_add_new_address() throws Throwable {
		if (oP.verifyAddressIsAdded()) {
			oP.clickOnAddNewAddress();
			oP.enterAddress1("Test1");
			oP.enterAddress2("Test2");
			oP.enterAddress3("Test3");
			oP.enterCity("Test City");
			oP.enterPostalCode(RandomStringUtils.randomNumeric(5));
			oP.selectState("Georgia");
			oP.enterMobile(RandomStringUtils.randomNumeric(10));
			oP.clickOnSaveAddressCheckboxIfSelected();
			oP.clickOnShipHere();
		} else {
			oP.enterAddress1("Test1");
			oP.enterAddress2("Test2");
			oP.enterAddress3("Test3");
			oP.enterCity("Test City");
			oP.enterPostalCode(RandomStringUtils.randomNumeric(5));
			oP.selectState("Georgia");
			oP.enterMobile(RandomStringUtils.randomNumeric(10));
		}
	}

	@When("^user select shipping method$")
	public void user_select_shipping_method() throws Throwable {
		oP.selectShippindMethod();
	}

	@When("^user click on next button$")
	public void user_click_on_next_button() throws Throwable {
		oP.clickOnNextButton();
	}

	@And("^user can able to fetch total price in summary page$")
	public void user_can_able_to_fetch_total_price_in_summary_page() throws Throwable {
		DataConstants.summary_price = oP.fetchPriceOnSummary();
		InitializeWebDriver.scenario.write("Price On Summary Page : " + DataConstants.summary_price);
	}

	@When("^user place the order$")
	public void user_place_the_order() throws Throwable {
		oP.clickOnPlaceOrder();
	}

	@Then("^user can able to see order number$")
	public void user_can_able_to_see_order_number() throws Throwable {
		DataConstants.order_num = oP.fetchOrderNum();
		InitializeWebDriver.scenario.write("Order Number : " + DataConstants.order_num);
	}

	@Then("^user can verify price on cart and summary page are same$")
	public void user_can_verify_price_on_cart_and_summary_page_are_same() throws Throwable {
		String discount = oP.getDiscountPrice();
		double discount_price = 0;
		if (!discount.equals("") || discount.equals(null))
			discount_price = convertToDouble(discount);
		String message = "Discount Price : " + discount + "\n";
		double price_on_cart = convertToDouble(DataConstants.cart_price),
				price_on_summary = convertToDouble(DataConstants.summary_price);
		DataConstants.final_price = price_on_summary + discount_price;
		message = message + DataConstants.final_price + "(Total Price) = " + price_on_summary + "(Order Price) + "
				+ discount_price + "(Discount Price)\n";
		if (price_on_cart != DataConstants.final_price)
			Assert.assertTrue(false);
		message = message + "Price on cart and summary page are same!";
		InitializeWebDriver.scenario.write(message);
	}

	public double convertToDouble(String string) {
		double result = 0;
		Pattern p = Pattern.compile("(\\d)+\\.(\\d)+");
		Matcher m = p.matcher(string);
		if (m.find())
			result = Double.parseDouble(m.group());
		return result;
	}

	@Given("^user navigate account page$")
	public void user_navigate_account_page() throws Throwable {
		oP.clickOnProfile();
		oP.clickOnMyAccount();
	}

	@Given("^user go to my order$")
	public void user_go_to_my_order() throws Throwable {
		oP.clickOnMyOrder();
	}

	@Then("^user can able to validate order$")
	public void user_can_able_to_validate_order() throws Throwable {
		Assert.assertTrue(oP.isOrderPlaces(DataConstants.order_num));
		String message = "Order Number : " + DataConstants.order_num + "is displayed in my order page\n";
		DataConstants.order_price = oP.fetchPriceOrder(DataConstants.order_num);
		message = message + "Price On Order Page : " + DataConstants.order_price + "\n";
		if (convertToDouble(DataConstants.cart_price) != DataConstants.final_price)
			Assert.assertTrue(false);
		message = message + "Price on cart and order page are same!";
		InitializeWebDriver.scenario.write(message);
	}

}