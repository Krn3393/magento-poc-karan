### Selenium Framework with Cucumber for Magento Automation

BDD framework for automation using Selenium Cucumber and TestNg

The framework has following features 

1. Modular Design
2. Maven based framework
3. Log4j enabled for logging
4. Report Generation (cucumber-reporting) 
5. Helper class to handle web component such as (Button,Link etc)
6. Centralized Configuration (Using Properties file)
7. POM

### Here is the basic code:

To use the class for handling the web component create the object and use it

```java
	GridHelper grid = new GridHelper(driver);
	grid.typeInGrid(item,GridLocator.cartId,1,1,qty);
```

### Add the Feature file 

Add the feature file under `test\resources\featurefile`

```java
Feature: Create & Validate Order

  Scenario Outline: Select Jackets
    Given user search "<Item>" in "<Category>" for "<Menu>"
    Then user is able to see resulted products
    When user select first product
    And user select size and color
    And user select quantity "<Qty>"
    And user add the product to cart
    Then user can able to see cart value updated

    Examples: 
      | Item    | Category | Menu | Qty |
      | Jackets | Tops     | Men  |   2 |

  Scenario Outline: Select Pants
    Given user search "<Item>" in "<Category>" for "<Menu>"
    Then user is able to see resulted products
    When user select first product
    And user select size and color
    And user select quantity "<Qty>"
    And user add the product to cart
    Then user can able to see cart value updated

    Examples: 
      | Item  | Category | Menu | Qty |
      | Pants | Bottoms  | Men  |   1 |

  Scenario: Place Order
    Given user navigate to cart
    Then user can able to fetch total cart price
    And user go to checkout page
    When user add new address
    And user select shipping method
    And user click on next button
    And user can able to fetch total price in summary page
    Then user can verify price on cart and summary page are same
    And user place the order
    Then user can able to see order number

  Scenario: Verify Order
    Given user navigate account page
    And user go to my order
    Then user can able to validate order

```

### Create the Runner

```java
/**
 * @author automation_user
 *	
 */
package com.magento.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "classpath:featurefile/CreateOrder.feature" }, glue = {
		"classpath:com.magento.framework.stepdefinition",
		"classpath:com.magento.framework.helper" }, plugin = { "pretty", "json:target/CreateOrderRunner.json" })

public class CreateOrderRunner extends AbstractTestNGCucumberTests {
}
``` 

### Use the testng.xml file to run the test cases

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite">
	<listeners>
		<listener
			class-name="com.magento.framework.listeners.reportlistener.CucumberReport" />
	</listeners>
	<test name="Test - 1">
		<classes>
			<class name="com.magento.framework.runner.CreateOrderRunner" />
		</classes>
	</test>
</suite> 	
```

### To see this whole thing running simply checkout this project and run this command:

`mvn clean generate-sources test` or `right click on project and run as maven test`

### Cucumber Report

Reprot is available inside target > test-classes > reports directory

There is a feature overview page:
And there are also feature specific results pages:
And useful information for failures:
If you have tags in your cucumber features you can see a tag overview:
And you can drill down into tag specific reports:

### Screenshots

Failure screenshots are available inside target target > test-classes > screenshots directory

Success screenshots are available inside Success_Screenshots directory