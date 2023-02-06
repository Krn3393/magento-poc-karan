
package com.magento.framework.listeners.reportlistener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.magento.framework.helper.Logger.LoggerHelper;
import com.magento.framework.settings.ObjectRepo;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * @author automation_user
 * 
 *         21-Aug-2016
 *
 */
public class CucumberReport implements ISuiteListener {

	public final Logger oLog = LoggerHelper.getLogger(CucumberReport.class);

	@Override
	public void onStart(ISuite suite) {

	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			if (ObjectRepo.driver != null) {
				ObjectRepo.driver.quit();
				ObjectRepo.reader = null;
				ObjectRepo.driver = null;
				oLog.info("Shutting Down the driver");
			}

			File jsonfile = new File("target/");
			File reportOutputDirectory = new File("target/test-classes/reports/cucumberreports/");

			String[] fileNames = jsonfile.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".json"))
						return true;
					return false;
				}
			});

			for (int i = 0; i < fileNames.length; i++) {
				fileNames[i] = jsonfile.getAbsolutePath() + "/" + fileNames[i];
			}

			List<String> jsonFiles = Arrays.asList(fileNames);

			Configuration configuration = new Configuration(reportOutputDirectory, suite.getName());
			configuration.setStatusFlags(true, true, true);

			ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
			reportBuilder.generateReports();
			oLog.info("Report Generated : " + configuration.getReportDirectory());

		} catch (Exception e) {
			oLog.error(e);
			throw e;
		}
	}

}
