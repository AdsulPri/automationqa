package com.hostelworld.stepDefinition;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hostelworld.utils.ReportingUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class cityHostelSearch {
	WebDriver driver;
	String testCityName = null;
	static ExtentTest test;
	static ExtentReports report;
	String scenarioName=null;

	@Before
	public void setUp(Scenario scenario) {
		scenarioName = scenario.getName();
		if (scenarioName.contains("navigation"))
		{
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//test//resources//chrome//chromedriver1.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

		// Reporting
		
		String reportName = scenarioName.replaceAll(" ", "");
		reportName = scenarioName.replaceAll("\"", "");
		report = new ExtentReports(System.getProperty("user.dir") + "\\target\\reports\\" + reportName + ".html");
		test = report.startTest(scenarioName);
	}

	@Given("^user is on hostelworld homepage$")
	public void user_is_on_hostelworld_homepage() throws IOException {
		try {
			driver.get("https://www.hostelworld.com");
			test.log(LogStatus.PASS,
					test.addScreenCapture(ReportingUtility.capture(driver)) + "Navigated to Hostel world website");
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					test.addScreenCapture(ReportingUtility.capture(driver)) + "Error while navigating to Hostel world website");
			driver.quit();
		}
	}

	@When("^user enters city name as \"([^\"]*)\"$")
	public void user_enters_city_name_as(String cityName) throws IOException {
		testCityName = cityName;
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.elementToBeClickable(By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']")))
					.click();
			new WebDriverWait(driver, 20)
					.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']")))
					.sendKeys(cityName);
			test.log(LogStatus.PASS,
					test.addScreenCapture(ReportingUtility.capture(driver)) + "Entered city name as " + cityName);
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					test.addScreenCapture(ReportingUtility.capture(driver)) + "Error while entering city name as " + cityName);
			driver.quit();
		}
	}

	@When("^selects the same from list$")
	public void selects_the_same_from_list() throws IOException {
		try {
			List<WebElement> myList = new WebDriverWait(driver, 20)
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(
							"#top-search > div.d-block.d-block.search-form-background.pt-2.pb-2 > div > div.suggestions-container > ul > li.hover")));
			test.log(LogStatus.PASS, test.addScreenCapture(ReportingUtility.capture(driver)) + "Selecting city from list");
			for (WebElement element : myList)
				if (element.getText().contains(testCityName)) {
					element.click();
				}
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					test.addScreenCapture(ReportingUtility.capture(driver)) + "Error while selecting city from list");
			driver.quit();
		}
	}

	@When("^clicks on search button$")
	public void clicks_on_search_button() throws InterruptedException {
		try {
			WebElement search = driver.findElement(By.cssSelector("#top-search > div:nth-child(2) > button"));
			search.click();
			test.log(LogStatus.PASS, "Starting search");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Error while starting search");
			driver.quit();
		}
	}

	@Then("^the hostels loaded are for same \"([^\"]*)\" city$")
	public void the_hostels_loaded_are_for_same_city(String cityName) throws IOException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			WebElement element = driver.findElement(By.cssSelector(
					"#pagebody > div.off-canvas-wrap > div.inner-wrap > div.page-contents.frcx > div.covercontainer > div.overlay-header-back > div > div > div > span"));
			String searchedCity = element.getText();
			System.out.println(searchedCity);
			if (cityName.equalsIgnoreCase(searchedCity)) {
				test.log(LogStatus.PASS, test.addScreenCapture(ReportingUtility.capture(driver))
						+ "Page loaded for respective city :" + searchedCity);
			} else {
				System.out.println("Test case result is FAIL for city :: " + searchedCity);
				test.log(LogStatus.PASS, test.addScreenCapture(ReportingUtility.capture(driver))
						+ "Page is not loaded for respective city :" + searchedCity);
			}
		} catch (StaleElementReferenceException e) {

			WebElement element = driver.findElement(By.cssSelector(
					"#pagebody > div.off-canvas-wrap > div.inner-wrap > div.page-contents.frcx > div.covercontainer > div.overlay-header-back > div > div > div > span"));
			String searchedCity = element.getText();
			if (cityName.equalsIgnoreCase(searchedCity)) {
				test.log(LogStatus.PASS, test.addScreenCapture(ReportingUtility.capture(driver))
						+ "Page loaded for respective city :" + searchedCity);
			} else {
				System.out.println("Test case result is FAIL for city :: " + searchedCity);
				test.log(LogStatus.PASS, test.addScreenCapture(ReportingUtility.capture(driver))
						+ "Page is not loaded for respective city :" + searchedCity);
			}
		}
	}

	@After
	public void tearDown() {
		if (scenarioName.contains("navigation"))
		{
		driver.quit();
		}
		report.endTest(test);
		report.flush();
	}

}
