package com.hostelworld.stepDefinition;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class logtest {
	static Logger logger = Logger.getLogger(logtest.class);

	public static void main(String[] args) {
		// PropertiesConfigurator is used to configure logger from properties
		// file
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\main\\resources\\config\\log.properties");

		// Log in console in and log file
		logger.info("Log4j appender configuration is successful !!");
	}
}
