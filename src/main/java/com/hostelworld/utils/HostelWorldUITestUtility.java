package com.hostelworld.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HostelWorldUITestUtility {
	private static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Ireland\\Hostelworld\\src\\test\\resources\\chrome\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.hostelworld.com");

		WebElement selectCity = driver.findElement(By.xpath("//*[@id='home-search-keywords']"));
		new WebDriverWait(driver, 20).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']")))
				.click();

		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']")))
				.sendKeys("Mumbai");

		List<WebElement> myList = new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(
						"#top-search > div.d-block.d-block.search-form-background.pt-2.pb-2 > div > div.suggestions-container > ul > li.hover")));
		for (WebElement element : myList)
			if (element.getText().contains("Mumbai"))
				element.click();

		WebElement find = driver.findElement(By.cssSelector("#top-search > div:nth-child(2) > button"));
		find.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String searchedCity = driver
				.findElement(By.cssSelector("#top-search > div > div.input-location.location-field"))
				.getAttribute("value");

		System.out.println("Verifying city :: " + searchedCity+ "and selected city"+selectCity.getText());
		driver.quit();

	}

}