package seleniumTests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class hostelWorldHomePage {
	private static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Ireland\\Hostelworld\\src\\test\\resources\\chrome\\chromedriver1.exe"
		);
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.hostelworld.com");
		
		//*[@id="home-search-keywords"]
		
		WebElement selectCity = driver.findElement(By.xpath("//*[@id='home-search-keywords']"));
		//selectCity.clear();
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable
				(By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']"))).click();
		
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable
				(By.xpath("//input[@name='search_keywords' and @id='home-search-keywords']"))).sendKeys("Mumbai");
		
		List<WebElement> myList = new WebDriverWait(driver, 20).
				until(ExpectedConditions.visibilityOfAllElementsLocatedBy
						(By.cssSelector("#top-search > div.d-block.d-block.search-form-background.pt-2.pb-2 > div > div.suggestions-container > ul > li.hover")));
		for (WebElement element:myList)
		    if(element.getText().contains("Mumbai"))
		        element.click();
		
		
		WebElement find = driver.findElement(By.cssSelector("#top-search > div:nth-child(2) > button"));
		find.click();


		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String searchedCity = driver.findElement(By.cssSelector("#top-search > div > div.input-location.location-field")).getAttribute("value");

		
		
		
		System.out.println("Verifying city :: "+searchedCity);

		/*driver.navigate().to("http://shop.demoqa.com/?s=" + "dress" + "&post_type=product");

		List<WebElement> items = driver.findElements(By.cssSelector(".noo-product-inner"));
		items.get(0).click();

		WebElement addToCart = driver.findElement(By.cssSelector("button.single_add_to_cart_button"));
		addToCart.click();*/

		/*WebElement continueToCheckout = driver.findElement(By.cssSelector(".checkout-button.alt"));
		continueToCheckout.click();

		Thread.sleep(5000);
		WebElement firstName = driver.findElement(By.cssSelector("#billing_first_name"));
		firstName.sendKeys("Lakshay");

		WebElement lastName = driver.findElement(By.cssSelector("#billing_last_name"));
		lastName.sendKeys("Sharma");

		WebElement emailAddress = driver.findElement(By.cssSelector("#billing_email"));
		emailAddress.sendKeys("test@gmail.com");

		WebElement phone = driver.findElement(By.cssSelector("#billing_phone"));
		phone.sendKeys("07438862327");

		WebElement countryDropDown = driver.findElement(By.cssSelector("#billing_country_field .select2-arrow"));
		countryDropDown.click();
		Thread.sleep(2000);

		List<WebElement> countryList = driver.findElements(By.cssSelector("#select2-drop ul li"));
		for (WebElement country : countryList) {
			if (country.getText().equals("India")) {
				country.click();
				Thread.sleep(3000);
				break;
			}
		}

		WebElement city = driver.findElement(By.cssSelector("#billing_city"));
		city.sendKeys("Delhi");

		WebElement address = driver.findElement(By.cssSelector("#billing_address_1"));
		address.sendKeys("Shalimar Bagh");

		WebElement postcode = driver.findElement(By.cssSelector("#billing_postcode"));
		postcode.sendKeys("110088");

		WebElement shipToDifferetAddress = driver.findElement(By.cssSelector("#ship-to-different-address-checkbox"));
		shipToDifferetAddress.click();
		Thread.sleep(3000);

		List<WebElement> paymentMethod = driver.findElements(By.cssSelector("ul.wc_payment_methods li"));
		paymentMethod.get(0).click();

		WebElement acceptTC = driver.findElement(By.cssSelector("#terms.input-checkbox"));
		acceptTC.click();

		WebElement placeOrder = driver.findElement(By.cssSelector("#place_order"));
		placeOrder.submit();*/

		driver.quit();

	}

}