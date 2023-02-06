
package com.magento.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "classpath:featurefile/CreateOrder.feature" }, glue = {
		"classpath:com.magento.framework.stepdefinition",
		"classpath:com.magento.framework.helper" }, plugin = { "pretty", "json:target/CreateOrderRunner.json" })

public class CreateOrderRunner extends AbstractTestNGCucumberTests {

}
