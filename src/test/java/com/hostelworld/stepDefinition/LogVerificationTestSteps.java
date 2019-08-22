package com.hostelworld.stepDefinition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.relevantcodes.extentreports.ExtentTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LogVerificationTestSteps {
	Logger log = Logger.getLogger(LogVerificationTestSteps.class);
	HashMap<String, String> map = new HashMap<String, String>();
	HashMap<String, String> errormap = new HashMap<String, String>();
	String filePath = null;

	String testCityName = null;
	ExtentTest test;

	@Given("^user has a file as \"([^\"]*)\"$")
	public void user_has_a_file_as(String fileName) throws Throwable {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime today = LocalDateTime.now();
		String timeStamp = formatter.format(today); 
		String dateTime = timeStamp.replaceAll(":", "");
		String[] file = fileName.split("\\.");
		System.setProperty("logfilename", "verifyFileValues_" +file[0].toString()+ dateTime);
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/config/log4j.properties");

		try {
			filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/" + fileName;
			log.info("**********");
			log.info("Starting Test");
			log.info("File :" + filePath);
		} catch (Exception e) {
			log.info("File :" + filePath + e.getStackTrace());
		}
	}

	@When("^value for each line is in between upper limit \"([^\"]*)\" and lower limit \"([^\"]*)\" inclusive$")
	public void value_for_each_line_is_in_between_upper_limit_and_lower_limit_inclusive(int upperLimit, int lowerLimit)
			throws Throwable {
		try {
			log.info("Upper limit (inclusive) is :" + upperLimit);
			log.info("Lower limit (inclusive) is :" + lowerLimit);
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\t", 2);
				if (parts.length >= 2) {
					String key = parts[0];
					String value = parts[1];
					int v = Integer.parseInt(value.trim());
					if (v <= lowerLimit || v >= upperLimit) {
						if (v < lowerLimit) {
							errormap.put("line number: " + key, " error:" + v + " is less than 100");
						}
						if (v > upperLimit) {
							errormap.put("line number :" + key, " error: " + v + " is greater than 500");
						}
					}
					map.put(key, value);
				} else {
					log.info("ignoring line: " + line);
				}
			}

			reader.close();
		} catch (Exception e) {
			log.error("Issue while checking upper and lower limit of file");

		}
	}

	@Then("^the file is \"([^\"]*)\"$")
	public void the_file_is(String isValid) throws Throwable {
		if (errormap.isEmpty()) {
			log.info("Test status is PASS");

		} else {
			log.info("Test status is FAIL");
			log.error("Errors in file:");
			for (String key : errormap.keySet()) {
				log.debug(key + " " + errormap.get(key));
			}
		}

		log.info("End of test");
		log.info("**********");
	}
}
