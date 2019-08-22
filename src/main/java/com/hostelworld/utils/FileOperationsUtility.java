package com.hostelworld.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FileOperationsUtility {

	public static void main(String[] args) throws IOException {
		String filePath = "C:\\Users\\Adsul\\Desktop\\TestData\\randomtext.txt";
		
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> errormap = new HashMap<String, String>();
		Logger log = Logger.getLogger(FileOperationsUtility.class);
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + "/src/main/resources/config/log4j.properties");
		

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

		log.info("Given entries in file :::");
		for (String key : map.keySet()) {

			log.info(key + "=" + map.get(key));
		}

		log.info("Errors in file:");
		for (String key : errormap.keySet()) {
			log.info(key + " " + errormap.get(key));
		}
	}
	
	public static String getConfigProperty(String parameter) throws IOException{
	FileReader reader=new FileReader(System.getProperty("user.dir") + "/src/main/resources/config/config.properties");
	Properties p=new Properties();
	p.load(reader);
	String value=p.getProperty(parameter);
	return  value;
	}
	
	public static void setConfigProperty(String value, String parameter) throws IOException{
		FileReader reader=new FileReader(System.getProperty("user.dir") + "/src/main/resources/config/config.properties");
		Properties p=new Properties();
		p.load(reader);
		p.setProperty(value, parameter);
		
		}
}
