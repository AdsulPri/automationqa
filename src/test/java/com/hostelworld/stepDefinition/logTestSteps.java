package com.hostelworld.stepDefinition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hostelworld.utils.FileOperationsUtility;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class logTestSteps {
	Logger log = Logger.getLogger(FileOperationsUtility.class);
	HashMap<String, String> map = new HashMap<String, String>();
	HashMap<String, String> errormap = new HashMap<String, String>();
	String filePath = null;

	@Given("^I have a file as \"([^\"]*)\"$")
	public void i_have_a_file_as(String arg1) throws Throwable {

		PropertyConfigurator
				.configure(System.getProperty("user.dir") + "\\src\\main\\resources\\config\\log4j.properties");

		filePath = System.getProperty("user.dir") +"\\src\\test\\resources\\testdata\\randomtext.txt";

	}

	@When("^value for each line is in betweenr upper limit \"([^\"]*)\" and lower limit \"([^\"]*)\" inclusive$")
	public void value_for_each_line_is_in_betweenr_upper_limit_and_lower_limit_inclusive(String arg1, String arg2)
			throws Throwable {
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split("\t", 2);
			if (parts.length >= 2) {
				String key = parts[0];
				String value = parts[1];
				int v = Integer.parseInt(value.trim());
				if (v < 100 || v > 500) {
					if (v < 100) {
						errormap.put("line number: " + key, " error:" + v + " is less than 100");
					}
					if (v > 500) {
						errormap.put("line number :" + key, " error: " + v + " is greater than 500");
					}
				}
				map.put(key, value);
			} else {
				log.info("ignoring line: " + line);
			}
		}

		reader.close();
	}

	@Then("^the file is \"([^\"]*)\"$")
	public void the_file_is(String arg1) throws Throwable {
		log.info("Given entries in file :::");
		for (String key : map.keySet()) {

			log.info(key + "=" + map.get(key));
		}

		log.info("Errors in file:");
		for (String key : errormap.keySet()) {
			log.info(key + " " + errormap.get(key));
		}
	}
}
