package maven_test.maven_project;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExpediaTest {
	
	private static WebDriver driver;
	
	WebDriverWait wait;
	Actions a;
	JavascriptExecutor js;
	WebElement calendar;
	List<WebElement> dates=new ArrayList<WebElement>();
	List<String> friday_dates=new ArrayList<String>();
	Select s;
	
	@BeforeTest
	public void launch_site() throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		
		driver=new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		
		a=new Actions(driver);
		js = (JavascriptExecutor)driver;
		wait=new WebDriverWait(driver, 3000);
		
		driver.get("https://www.expedia.ca/");
		
		driver.manage().window().maximize();
		
	}
	
	@AfterTest
	public void close_site() {

		//driver.quit()
		
	}
	
	@Test (priority=1)
	public void validate_logo() {
		//This test validates that Expedia logo is displayed in the home page and it is clickable
		WebElement logo=driver.findElement(By.cssSelector("a.uitk-header-brand-logo"));
		wait.until(ExpectedConditions.visibilityOf(logo));
		Assert.assertTrue(logo.isDisplayed());
		Assert.assertTrue(logo.isEnabled());
	}
	
	@Test(priority=2)
	public void validate_tabs_homepage() {
		
		//This test validate that home page has tabs -Stays, Flights, Cars, Packages, Things to Do, Cruises and
		//the tabs are clickable. Also validates underline bar is displayed when hovering on each tab option
		
		//Common css selector to hold all tab locators in a list
		List<WebElement> tabs=new ArrayList<WebElement>();
		tabs=driver.findElements(By.cssSelector("div#multi-product-search-form-1>div>div>div:nth-of-type(1)>ul>li[role='presentation']"));
		
		//Validate that 6 tabs are displayed
		Integer number_of_tabs=tabs.size();
		Assert.assertEquals(number_of_tabs, 6);
		
		//Click each tab and validate that they are clickable
		for(WebElement tab:tabs) {
			tab.click();
		}
		
	}
	
	
	@Test(priority=3)
	public void booking_car() throws InterruptedException {
		
		//Click on Car tab
		WebElement car_tab=driver.findElement(By.cssSelector("div#multi-product-search-form-1>div>div>div:nth-of-type(1)>ul>li[role='presentation']:nth-of-type(3)"));
		wait.until(ExpectedConditions.visibilityOf(car_tab));
		car_tab.click();
		
		//Enter pickup location
		WebElement pickup_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#car_search_form>div>div:nth-child(1)>div>div>div:nth-child(2)")));
		
		try {
		//wait.until(ExpectedConditions.visibilityOf(pickup_button));
		pickup_button.click();
		}catch(NoSuchElementException e) {
			driver.navigate().refresh();
			//wait.until(ExpectedConditions.visibilityOf(pickup_button));
			pickup_button.click();
		}
		
		//Key in pickup location as Surrey
		WebElement input_pickup=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#pick_up_location")));
		a.moveToElement(input_pickup).click().build().perform();
		input_pickup.sendKeys("Surrey");
		
		//Select Surrey Newton as pickup location
		WebElement pickup_location=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='uitk-sheet-content uitk-layout-flex-item uitk-typeahead-menu-content'] ul>div:nth-child(2)")));
		pickup_location.click();
		
		//Click Date field
		WebElement date_field=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#car_search_form>div>div:nth-child(3)>div>div>div>div>button")));
		date_field.click();
		
		//Bring the intended month in calendar view. For this code month is July
		boolean found=false;
		String month_left_text="";
		String month_right_text="";
		
		while(found==false){
			
			WebElement month_left_header=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.uitk-cal-controls>div:nth-child(3)>div>div:nth-child(1)>span")));
			month_left_text=month_left_header.getText();
			
			WebElement month_right_header=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.uitk-cal-controls>div:nth-child(3)>div>div:nth-child(2)>span")));
			month_right_text=month_right_header.getText();
						
			WebElement arrow_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.uitk-cal-controls>div:nth-child(2)>button")));
			
			if(month_left_text.contains("June") ||month_right_text.contains("June")) {
				found=true;
				break;
				
			}else {
				arrow_button.click();
				found=false;
			}
			
		}
		
		//Code to select date
		
		if(month_left_text.contains("June")) {
			
			dates=driver.findElements(By.cssSelector("div.uitk-cal-controls>div:nth-child(3)>div>div:nth-child(1)>span+table>tbody>tr>td>div"));
			
		}else if(month_right_text.contains("June")) {
			
			dates=driver.findElements(By.cssSelector("div.uitk-cal-controls>div:nth-child(3)>div>div:nth-child(2)>span+table>tbody>tr>td>div"));
			
		}
		
		//Iterate through all date web elements and click on intended date.
		for(WebElement date:dates) {
			String date_value=date.findElement(By.cssSelector("[class='uitk-day-aria-label']")).getAttribute("aria-label");
			System.out.println(date_value);
			if(date_value.contains("June 8")){
				date.click();
				break;
			}
		}
		
		//Click on done button after clicking the date.
		WebElement done_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#car_search_form > div > div:nth-child(3) > div > section > footer > div > button")));
		done_button.click();
		
		//select pickup time and drop off time as 4PM
		WebElement pickup_time=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#pick_up_time")));
		WebElement dropoff_time=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#drop_off_time")));
		s=new Select(pickup_time);
		s.selectByValue("0400PM");
		
		s=new Select(dropoff_time);
		s.selectByValue("0400PM");
		
		//Click search button
		WebElement search_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button#search_button")));
		
		search_button.click();

		


		
	}
	
	

}
