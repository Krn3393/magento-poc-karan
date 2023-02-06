package com.magento.helper.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.magento.framework.helper.BasePageObject.PageBase;
import com.magento.framework.helper.Button.ButtonHelper;
import com.magento.framework.helper.DropDown.DropDownHelper;
import com.magento.framework.helper.Generic.GenericHelper;
import com.magento.framework.helper.MouseEvent.MouseEventHelper;
import com.magento.framework.helper.TextBox.TextBoxHelper;
import com.magento.framework.helper.Wait.WaitHelper;
import com.magento.framework.settings.ObjectRepo;

public class OrderPage extends PageBase {

	private WebDriver driver;

//	private final static Logger log = LoggerHelper.getLogger(OrderPage.class);
	private ButtonHelper bHelper;
	private TextBoxHelper tHelper;
	private MouseEventHelper mHelper;
	private GenericHelper gHelper;
	private DropDownHelper dHelper;
	private WaitHelper wHelper;

	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		bHelper = new ButtonHelper(driver);
		tHelper = new TextBoxHelper(driver);
		mHelper = new MouseEventHelper(driver);
		gHelper = new GenericHelper(driver);
		dHelper = new DropDownHelper(driver);
		wHelper = new WaitHelper(driver, ObjectRepo.reader);
	}

	/** Web Elements */

	@FindBy(xpath = "(//*[contains(@class,'product name')]//a)[1]")
	WebElement lnkFirstProduct;

	@FindBy(xpath = "(//div[contains(@id,'option-label-size')])[1]")
	WebElement lnkFirstSize;

	@FindBy(xpath = "(//div[contains(@id,'option-label-color')])[1]")
	WebElement lnkFirstColor;

	@FindBy(id = "qty")
	WebElement txtqty;

	@FindBy(id = "product-addtocart-button")
	WebElement btnAddToCart;

	@FindBy(id = "top-cart-btn-checkout")
	WebElement btnCheckout;

	@FindBy(xpath = "//*[@class='action showcart']")
	WebElement btnViewCart;

	By shippingAddress = By.xpath("(//*[@class='shipping-address-item selected-item'])[1]");

	@FindBy(xpath = "//*[text()='New Address']")
	WebElement btnAddNewAddress;

	@FindBy(xpath = "//*[@name='street[0]']")
	WebElement txtAddress1;

	@FindBy(xpath = "//*[@name='street[1]']")
	WebElement txtAddress2;

	@FindBy(xpath = "//*[@name='street[2]']")
	WebElement txtAddress3;

	@FindBy(name = "city")
	WebElement txtCity;

	@FindBy(name = "region_id")
	WebElement drpRegionId;

	@FindBy(name = "postcode")
	WebElement txtPostCode;

	@FindBy(name = "telephone")
	WebElement txtTelephone;

	@FindBy(id = "shipping-save-in-address-book")
	WebElement btnSaveAddress;

	@FindBy(xpath = "//*[text()='Ship here']")
	WebElement btnShipHere;

	@FindBy(xpath = "//*[@value='tablerate_bestway']")
	WebElement btnShippingMethodBestway;

	@FindBy(xpath = "(//li[@class='customer-welcome']//*[@class='action switch'])[1]")
	WebElement iconProfileDown;

	@FindBy(xpath = "(//a[text()='My Account'])[1]")
	WebElement menuMyAccount;

	@FindBy(xpath = "(//a[text()='My Orders'])[1]")
	WebElement menuMyOrders;

	@FindBy(xpath = "//*[@data-role='opc-continue']")
	WebElement btnNext;

	@FindBy(xpath = "//*[@class='action primary checkout']")
	WebElement btnPlaceOrder;

	@FindBy(xpath = "//*[@class='amount price-container']//*[@class='price']")
	WebElement lblPriceOnCart;

	@FindBy(xpath = "//td[@data-th='Order Total']//*[@class='price']")
	WebElement lblPriceOnSummary;

	@FindBy(xpath = "//*[text()='Your order number is: ']//strong")
	WebElement lblOrder;

	@FindBy(xpath = "//*[text()='Discount']/..//following-sibling::*//*[@class='price']")
	WebElement lblDiscountPrice;

	String commonNavigation = "(//li//span[text()='%s'])[last()]";

	String orderList = "//*[@data-th=\"Order #\" and text()='%s']";

	String orderValueList = "//*[@data-th=\"Order #\" and text()='%s']//following-sibling::td[@data-th='Order Total']";

	By products = By.xpath("//*[contains(@class,'product name')]//a");

	By cart = By.xpath("//*[@class='action showcart']//*[@class='counter qty']");

	/** Public Methods **/
	public WebDriver getDriver() {
		return this.driver;
	}

	private void mouseHoverToMenu(String menu) {
		try {
			mHelper.moveToElement(By.xpath(String.format(commonNavigation, menu)));
		} catch (Exception e) {
		}
	}

	private void clickOnToSubMenu(String menu) {
		try {
			bHelper.click(By.xpath(String.format(commonNavigation, menu)));
		} catch (Exception e) {
		}
	}

	public void navigateToProductList(String arg1, String arg2, String arg3) {
		mouseHoverToMenu(arg3);
		mouseHoverToMenu(arg2);
		mouseHoverToMenu(arg1);
		clickOnToSubMenu(arg1);
	}

	public void selectSize() {
		try {
			bHelper.click(lnkFirstSize);
		} catch (Exception e) {
		}
	}

	public void selectColor() {
		try {
			bHelper.click(lnkFirstColor);
		} catch (Exception e) {
		}
	}

	public void selectFirstProduct() {
		try {
			bHelper.click(lnkFirstProduct);
		} catch (Exception e) {
		}
	}

	public void enterQty(String qty) {
		try {
			tHelper.clearAndSendKeys(txtqty, qty);
		} catch (Exception e) {
		}
	}

	public boolean verifyProductsAreDisplayes() {
		return gHelper.IsElementPresentQuick(products);
	}

	public void clickOnAddToCart() {
		try {
			bHelper.click(btnAddToCart);
		} catch (Exception e) {
		}
	}

	public void clickOnViewCart() {
		try {
			bHelper.click(btnViewCart);
		} catch (Exception e) {
		}
	}

	public void clickOnCheckout() {
		try {
			bHelper.click(btnCheckout);
		} catch (Exception e) {
		}
	}

	public void clickOnAddNewAddress() {
		try {
			bHelper.click(btnAddNewAddress);
		} catch (Exception e) {
		}
	}

	public boolean verifyAddressIsAdded() {
		return gHelper.IsElementPresentQuick(shippingAddress);
	}

	public boolean verifyCartUpdated() {
		return gHelper.IsElementPresentQuick(cart);
	}

	public void enterAddress1(String add) {
		try {
			tHelper.clearAndSendKeys(txtAddress1, add);
		} catch (Exception e) {
		}
	}

	public void enterAddress2(String add) {
		try {
			tHelper.clearAndSendKeys(txtAddress2, add);
		} catch (Exception e) {
		}
	}

	public void enterAddress3(String add) {
		try {
			tHelper.clearAndSendKeys(txtAddress3, add);
		} catch (Exception e) {
		}
	}

	public void enterCity(String city) {
		try {
			tHelper.clearAndSendKeys(txtCity, city);
		} catch (Exception e) {
		}
	}

	public void enterPostalCode(String postalCode) {
		try {
			tHelper.clearAndSendKeys(txtPostCode, postalCode);
		} catch (Exception e) {
		}
	}

	public void enterMobile(String mobile) {
		try {
			tHelper.clearAndSendKeys(txtTelephone, mobile);
		} catch (Exception e) {
		}
	}

	public void selectState(String state) {
		try {
			dHelper.SelectUsingVisibleValue(drpRegionId, state);
		} catch (Exception e) {
		}
	}

	public String fetchPriceOnCart() {
		String price = "";
		try {
			price = tHelper.getText(lblPriceOnCart);
			gHelper.takeScreenShotSuccess("Price_On_Cart_Page");
		} catch (Exception e) {
		}
		return price;
	}

	public String fetchPriceOnSummary() {
		String price = "";
		try {
			wHelper.hardWait(5000);
			price = tHelper.getText(lblPriceOnSummary);
			gHelper.takeScreenShotSuccess("Price_On_Summary_Page");
		} catch (Exception e) {
		}
		return price;
	}

	public String fetchPriceOrder(Object orderNum) {
		String order_num = "";
		try {
			order_num = tHelper.getText(By.xpath(String.format(orderValueList, orderNum)));
			gHelper.takeScreenShotSuccess("Order_Number");
		} catch (Exception e) {
		}
		return order_num;
	}

	public String fetchOrderNum() {
		try {
			return tHelper.getText(lblOrder);
		} catch (Exception e) {
		}
		return null;
	}

	public void clickOnShipHere() {
		try {
			bHelper.click(btnShipHere);
		} catch (Exception e) {
		}
	}

	public void clickOnSaveAddressCheckboxIfSelected() {
		try {
			bHelper.click(btnSaveAddress);
		} catch (Exception e) {
		}
	}

	public void clickOnNextButton() {
		try {
			bHelper.click(btnNext);
		} catch (Exception e) {
		}
	}

	public void clickOnPlaceOrder() {
		try {
			bHelper.click(btnPlaceOrder);
		} catch (Exception e) {
		}
	}

	public void clickOnProfile() {
		try {
			bHelper.click(iconProfileDown);
		} catch (Exception e) {
		}
	}

	public void clickOnMyAccount() {
		try {
			bHelper.click(menuMyAccount);
		} catch (Exception e) {
		}
	}

	public void clickOnMyOrder() {
		try {
			bHelper.click(menuMyOrders);
		} catch (Exception e) {
		}
	}

	public void selectShippindMethod() {
		try {
			bHelper.click(btnShippingMethodBestway);
		} catch (Exception e) {
		}
	}

	public boolean isOrderPlaces(String orderNum) {
		return gHelper.IsElementPresentQuick(By.xpath(String.format(orderList, orderNum)));
	}

	public String getDiscountPrice() {
		String discount_price = "";
		try {
			discount_price = tHelper.getText(lblDiscountPrice);
			gHelper.takeScreenShotSuccess("Discount_Price");
		} catch (Exception e) {
		}
		return discount_price;
	}
}
