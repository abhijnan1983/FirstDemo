package maven_test.maven_project;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParallelTest {
	
	private static WebDriver driver;
	private static ThreadLocal<WebDriver> TC=new ThreadLocal<>();
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions a;
	
	@BeforeMethod
	public void initialize_driver() throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		TC.set(new ChromeDriver());
		driver=TC.get();
		driver.get("https://www.partsource.ca/");
		driver.manage().window().maximize();
		Thread.sleep(30000);
		wait=new WebDriverWait(driver,30);
		js = (JavascriptExecutor)driver;
		a=new Actions(driver);
		
		//Close welcome pop up
		/*
		 * WebElement
		 * popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"))
		 * ; wait.until(ExpectedConditions.visibilityOf(popup_close)).click();
		 */
		
		
		  String parent_window_id=driver.getWindowHandle(); Set<String>
		  all_windows=driver.getWindowHandles(); Iterator<String>
		  itr=all_windows.iterator();
		  
		  while(itr.hasNext()) { String window_id=itr.next(); String
		  url=driver.getCurrentUrl(); if((!(window_id.equals(parent_window_id))) &&
		  ((!url.contains("partsource")))) { driver.switchTo().window(window_id);
		  WebElement
		  popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"))
		  ; wait.until(ExpectedConditions.visibilityOf(popup_close)).click(); } }
		 
		 
		
		
	}
	

	
	@Test(priority=1)
	public void validate_logo() {
		
		
		//Validate the logo in home page.
		WebElement logo=driver.findElement(By.cssSelector("div#shopify-section-static-header-2 section div+div>div td a img"));
		wait.until(ExpectedConditions.visibilityOf(logo));
		Assert.assertTrue(logo.isDisplayed());
		
	}
	
	@Test(priority=2)
	public void validate_reserve_online_banner() {
		
		/*
		 * WebElement
		 * popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"))
		 * ; wait.until(ExpectedConditions.visibilityOf(popup_close)).click();
		 */
		  
		//Validate the message, Customers can now RESERVE Online and Pickup at participating stores!
		WebElement reserve_online_message=driver.findElement(By.cssSelector(" div#shopify-section-static-header-2>div:nth-of-type(1)>div:nth-of-type(1)"));
		wait.until(ExpectedConditions.visibilityOf(reserve_online_message));
		Assert.assertTrue(reserve_online_message.isDisplayed());
		
		
		
	}
	
	@Test(priority=3)
	public void find_store() throws InterruptedException {
		
		/*
		 * WebElement
		 * popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"))
		 * ; wait.until(ExpectedConditions.visibilityOf(popup_close)).click();
		 */
		  
		WebElement find_store_button=driver.findElement(By.cssSelector("td#desktop_myStore input"));
		wait.until(ExpectedConditions.elementToBeClickable(find_store_button));
		js.executeScript("arguments[0].click();", find_store_button);
		
		
		Thread.sleep(5000);
		WebElement postal_code_entry=driver.findElement(By.cssSelector("input#address_search"));
		wait.until(ExpectedConditions.visibilityOf(postal_code_entry));
		a.moveToElement(postal_code_entry).click().sendKeys("V3T5H6").build().perform();
		WebElement submit_button=driver.findElement(By.cssSelector("button#submitBtn"));
		wait.until(ExpectedConditions.visibilityOf(submit_button)).click();
		js.executeScript("window.scrollBy(0,250)", "");
		WebElement make_my_store_button=driver.findElement(By.cssSelector("input#btn_PS719"));
		wait.until(ExpectedConditions.visibilityOf(make_my_store_button));
		js.executeScript("arguments[0].click();", make_my_store_button);
		Thread.sleep(5000);
		
		WebElement popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"));
		wait.until(ExpectedConditions.visibilityOf(popup_close)).click();
		
	}
	

}
