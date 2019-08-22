package com.hostelworld.stepDefinition;

import java.net.HttpURLConnection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hostelworld.utils.FileOperationsUtility;
import com.hostelworld.utils.HttpAPITestUtility;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class APITestSteps {

	Logger log = Logger.getLogger(APITestSteps.class);
	HttpAPITestUtility apiUtils = new HttpAPITestUtility();
	String gistContent = null;
	String gistId = null;
	String gistUrl = null;
	String git_auth_key = null;
	HttpURLConnection connection = null;

	@Given("^user has file to share$")
	public void user_has_files_to_share() throws Throwable {

		System.setProperty("logfilename", "APITest_" + System.currentTimeMillis());
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/config/log4j.properties");

		gistContent = apiUtils.getJsonObject("Create Gist", false, "CreateGist", "This gist is created for create gist test case");
		
		log.info("Creating gist..");
	}

	@Given("^git access key$")
	public void git_access_key() throws Throwable {
		git_auth_key = FileOperationsUtility.getConfigProperty("git_personaltoken");
		log.info("Fetching OATH key from config.properties file");
	}

	@When("^create gist api is called$")
	public void create_gist_api_is_called() throws Throwable {
		connection = apiUtils.sendAPIRequest(git_auth_key, "create", gistContent);
		log.info("Calling gist API to create gist with given description, access and file content");

	}

	@Then("^gist URLs should be received$")
	public void gist_URLs_should_be_received() throws Throwable {
		String url = apiUtils.parseResponse(connection, "url");
		org.junit.Assert.assertTrue(url, true);
		System.out.println("URL is :" + url);
		log.info("TEST RESULT 1::Gist is created and URL is : " + url);

	}

	@Given("^user has gist URL$")
	public void user_has_gist_URL() throws Throwable {
		gistContent = apiUtils.getJsonObject("Gist created for get or delete request", false, "Gist", "This gist is created for request test case");
		git_auth_key = FileOperationsUtility.getConfigProperty("git_personaltoken");
		connection = apiUtils.sendAPIRequest(git_auth_key, "create", gistContent);
		gistId = apiUtils.parseResponse(connection, "id");
		System.out.println("gist id" + gistId);
		org.junit.Assert.assertTrue(gistId, true);
		log.info("Created a gist with gist id:" + gistId);
		connection.disconnect();

	}

	@When("^get gist api is called$")
	public void get_gist_api_is_called() throws Throwable {
		connection = apiUtils.sendAPIRequest(git_auth_key, "getcontent", gistContent);
		org.junit.Assert.assertTrue(connection.getURL().toString(), true);
		log.info("Calling get API for parameters of gist...");
	}

	@Then("^gist files should be received$")
	public void gist_files_should_be_received() throws Throwable {
		String fileContent = apiUtils.parseResponse(connection, "ALL");
		org.junit.Assert.assertTrue(fileContent, true);
		log.info("TEST RESULT 2 :: Gist contents:" + fileContent);
	}

	@When("^delete gist api is called$")
	public void delete_gist_api_is_called() throws Throwable {
		connection = apiUtils.sendAPIRequest(git_auth_key, "delete", gistId);
		log.info("Calling DELETE api for deleting gist");
		org.junit.Assert.assertTrue(connection.toString(), true);
	}

	@Then("^response status code should be (\\d+)$")
	public void response_status_code_should_be(int expectedStatus) throws Throwable {

		int status = connection.getResponseCode();
		System.out.println("Status :" + status);
		org.junit.Assert.assertEquals(expectedStatus, status);
		System.out.println("Status code  " + status);

		log.info("Expected response code >>" + expectedStatus);
		log.info("Actual response code >>" + status);
	}

	@Then("^gist should be deleted$")
	public void gist_should_be_deleted() throws Throwable {
		connection = apiUtils.sendAPIRequest(git_auth_key, "getcontent", gistContent);
		org.junit.Assert.assertFalse(connection.getURL().toString(), false);
		log.info("TEST RESULT 3 :: URL should not be found for deleted gist!!");
	}

}
