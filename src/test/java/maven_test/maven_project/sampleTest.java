package maven_test.maven_project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;



public class sampleTest {
	
	private static WebDriver driver;
	//private static ThreadLocal<WebDriver>threadlocaldriver=new ThreadLocal<>();
	
	
	WebDriverWait wait;
	Actions a;
	JavascriptExecutor js;
	String url;
	String brand_name;
	List<String> initial_active_brands;
	List<String> later_active_brands;
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
		
		driver.get("https://www.partsource.ca/");
		
		driver.manage().window().maximize();
	
		 
		
	}
	
	@AfterTest
	public void close_site() {

		//driver.quit()
		
		

	}
	
	
	@Test(priority=1)
	public void close_popup() throws InterruptedException {
		
		  String parent_window_id=driver.getWindowHandle(); Set<String>
		  all_windows=driver.getWindowHandles(); Iterator<String>
		  itr=all_windows.iterator();
		  
		  while(itr.hasNext()) {
		  String window_id=itr.next();
		  if(!(window_id.equals(parent_window_id))) {
		  driver.switchTo().window(window_id); 
		  WebElement popup_close=driver.findElement(By.cssSelector("div#m-1635532295580>div+div"));
		  wait.until(ExpectedConditions.visibilityOf(popup_close)).click();
		  
		  } 
		  }

		
	}

	
	@Test(priority=2)
	public void validate_logo() throws InterruptedException {
		//Validate the logo in home page.
		WebElement logo=driver.findElement(By.cssSelector("div#shopify-section-static-header-2 section div+div>div td a img"));
		wait.until(ExpectedConditions.visibilityOf(logo));
		Assert.assertTrue(logo.isDisplayed());
		
	}
	
	@Test(priority=3)
	public void validate_reserve_online_message() throws InterruptedException {
		//Validate the message, Customers can now RESERVE Online and Pickup at participating stores!
		WebElement reserve_online_message=driver.findElement(By.cssSelector(" div#shopify-section-static-header-2>div:nth-of-type(1)>div:nth-of-type(1)"));
		wait.until(ExpectedConditions.visibilityOf(reserve_online_message));
		Assert.assertTrue(reserve_online_message.isDisplayed());
		
	}
	
	
	@Test(priority=4)
	public void find_store() throws InterruptedException {
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
	
	@Test(priority=5)
	public void verify_espot_rolling_images() throws InterruptedException {
		
		//Verify that rolling carousel banner is displayed with Valvoline and Castrol and then validate that
		//when user hovers on any carousel image then left and right arrow buttons are displayed
		WebElement image_one=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689863456011>div:nth-child(1)>div:nth-child(1)>div>div:nth-child(3)")));
		WebElement image_two=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689863456011>div:nth-child(1)>div:nth-child(1)>div>div:nth-child(4)")));
		
		Integer counter1=0;
		Integer counter2=0;
		
		while(counter1<10) {
			String image1_active=image_one.getAttribute("class");
			//System.out.println(image1_active);
			if(image1_active.contains("active")) {
				WebElement image=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689863456011>div:nth-child(1)>div:nth-child(1)>div>div:nth-child(3) img")));
				a.moveToElement(image).build().perform();
				WebElement arrow_left=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689863456011>div:nth-child(1)>div:nth-child(1)+div>button:nth-child(1)")));
				WebElement arrow_right=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689863456011>div:nth-child(1)>div:nth-child(1)+div>button:nth-child(2)")));
				Assert.assertTrue(arrow_left.isDisplayed());
				Assert.assertTrue(arrow_right.isDisplayed());
				
				WebElement logo=driver.findElement(By.cssSelector("div#shopify-section-static-header-2 section div+div>div td a img"));
				a.moveToElement(logo).build().perform();
				
				break;
			}else {
				counter1++;
				Thread.sleep(1000);
			}
		}
		
		while(counter2<10) {
			String image2_active=image_two.getAttribute("class");
			//System.out.println(image2_active);
			if(image2_active.contains("active")) {
				
				break;
				
			}else {
				counter2++;
				Thread.sleep(1000);
			}
		}
		
		
	}
	
	@Test(priority=6)
	public void verify_top_brands() {
		
		initial_active_brands=new ArrayList<String>();
		later_active_brands=new ArrayList<String>();
		//Store all brands in a list by using generic locator for the brands under top brands and then print number of brands
		List<WebElement> top_brands=new ArrayList<WebElement>();

		
		top_brands=driver.findElements(By.cssSelector("div#m-1689865380373>div>div:nth-child(1)>div>div"));
		System.out.println("Number of top brands "+top_brands.size());
		
		
		//Iterate through all the brands and print the brands which are currently displayed on the page.
		for(WebElement brand:top_brands) {
			
			String brand_active_status=brand.getAttribute("class");
			
			if(brand_active_status.contains("active")) {
			
			WebElement link=brand.findElement(By.tagName("a"));
			url=link.getAttribute("href");
			String brand_name=brand_from_url(url);
			initial_active_brands.add(brand_name);
			
			}
			
			
		}
		
		
		
		//Hover on the 6th brand displayed to enable the arrow buttons
		a.moveToElement(driver.findElement(By.cssSelector("div#m-1689865380373>div>div:nth-child(1)>div>div:nth-child(6)"))).build().perform();
		
		//Click on right arrow button 5 times to display new set of top brands on the page
		Integer number_of_click=0;
		
		while(number_of_click<6) {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#m-1689865380373>div>div:nth-child(2)>button:nth-child(2)"))).click();
			
			number_of_click++;
		}
		
		//Iterate through brands and get new list of active brands on the page
		for(WebElement brand:top_brands) {
			
			String brand_active_status=brand.getAttribute("class");
			
			if(brand_active_status.contains("active")) {
			
			WebElement link=brand.findElement(By.tagName("a"));
			url=link.getAttribute("href");
			String brand_name=brand_from_url(url);
			later_active_brands.add(brand_name);
			
			}
			
			
		}
		
		System.out.println(initial_active_brands);
		System.out.println(later_active_brands);
		

		

		
	}
	
	
	public String brand_from_url(String url) {
		Integer length_url=url.length();
		Integer last_slash_index=url.lastIndexOf("/");
		brand_name=url.substring(last_slash_index+1, length_url);
		return brand_name;
		
	}
	
	@Test(priority=7)
	public void verify_hiring_banner() {
		
		WebElement hiring_banner=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#e-1661522315904")));
		Assert.assertTrue(hiring_banner.isDisplayed());
		
	}
	
	@Test(priority=8)
	public void verify_findStore_banner() {
		
		WebElement findStore_banner=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#r-1573160384857")));
		Assert.assertTrue(findStore_banner.isDisplayed());
		
	}
	
	@Test(priority=9)
	public void validate_browse_parts_button() throws InterruptedException {
		
		//Validate that browse parts button is disabled until vehicle's Year, Make, Model, Trim and Engine selected
		WebElement button_browse_parts=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#vid_browse")));
		Assert.assertFalse(button_browse_parts.isEnabled());
		
		//Select vehicle year and ensure that browse parts button is disabled after vehicle year is selected
		//Model, Trim and Engine fields should be disabled after selecting the year
		WebElement v_year=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#ymm_year")));
		s=new Select(v_year);
		s.selectByVisibleText("2019");
		Assert.assertFalse(button_browse_parts.isEnabled());
		
		
		//Select vehicle make and ensure that browse parts button is disabled after vehicle year is selected
		//Vehicle model field should become enabled and trim, engine fields should remain disabled
		WebElement v_make=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#ymm_make")));
		s=new Select(v_make);
		s.selectByVisibleText("Toyota");
		Assert.assertFalse(button_browse_parts.isEnabled());
		
		//Select vehicle model and ensure after selection, the browse parts button is disabled
		WebElement v_model=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#ymm_model")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select#ymm_model")));
		s=new Select(v_model);
		s.selectByVisibleText("Camry");
		Assert.assertFalse(button_browse_parts.isEnabled());
		
		//Select vehicle trim and ensure after selection, browse parts button is disabled
		WebElement v_trim=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#ymm_trim")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select#ymm_trim")));
		s=new Select(v_trim);
		Thread.sleep(2000);
		s.selectByVisibleText("XLE");
		Assert.assertFalse(button_browse_parts.isEnabled());
		
		
		//Select vehicle engine and ensure that browse parts button is enabled after engine is selected
		WebElement v_engine=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select#ymm_engine")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select#ymm_engine")));
		s=new Select(v_engine);
		Thread.sleep(2000);
		s.selectByVisibleText("L4-152cid 2.5L FI A25A-FKS 203HP");
		
		wait.until(ExpectedConditions.elementToBeClickable(button_browse_parts));
		Assert.assertTrue(button_browse_parts.isEnabled());
		
		//Click Browse parts button
		button_browse_parts.click();
		
		//Validate the page which launches has buttons "Change Vehicle" and "Browse Parts" and also validate vehicle name displayed
		
		try{
			
			WebElement my_vehicle=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span")); 
			String my_vehicle_text=wait.until(ExpectedConditions.visibilityOf(my_vehicle)).getText(); 
			System.out.println(my_vehicle_text);
			 
			
			WebElement button_change_vehiicle=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span+input"));
			wait.until(ExpectedConditions.visibilityOf(button_change_vehiicle));
			Assert.assertTrue(button_change_vehiicle.isEnabled());
			
			WebElement button_browse_parts2=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span+input+input"));
			wait.until(ExpectedConditions.visibilityOf(button_browse_parts2));
			Assert.assertTrue(button_browse_parts2.isEnabled());
			
		}catch(StaleElementReferenceException e) {
			
			driver.navigate().refresh();
			Thread.sleep(3000);
			
			WebElement my_vehicle=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span")); 
			String my_vehicle_text=wait.until(ExpectedConditions.visibilityOf(my_vehicle)).getText(); 
			System.out.println(my_vehicle_text);
			 
			
			WebElement button_change_vehiicle=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span+input"));
			wait.until(ExpectedConditions.visibilityOf(button_change_vehiicle));
			Assert.assertTrue(button_change_vehiicle.isEnabled());
			
			WebElement button_browse_parts2=driver.findElement(By.cssSelector("div#YMM_bar>span:nth-child(2)>div>span+input+input"));
			wait.until(ExpectedConditions.visibilityOf(button_browse_parts2));
			Assert.assertTrue(button_browse_parts2.isEnabled());
			
			
			
		}

		
	}
	
	
	@Test(priority=10)
	public void validate_item_counts() {
		
		WebElement first_filter_option=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id*='boost-pfs-filter-tree2-pf-m-::mm-google-shopping::custom-label-2']>div>ul>li:nth-child(1)>button>span:nth-child(2)")));
		String first_filter_option_text=first_filter_option.getText();
		
		WebElement first_filter_option_count=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id*='boost-pfs-filter-tree2-pf-m-::mm-google-shopping::custom-label-2']>div>ul>li:nth-child(1)>button>span:nth-child(3)")));
		Integer first_filter_option_count_number=Integer.parseInt(first_filter_option_count.getText().substring(1, 2));
		
		System.out.println(first_filter_option_text);
		System.out.println(first_filter_option_count_number);
		
		WebElement first_filter_option_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id*='boost-pfs-filter-tree2-pf-m-::mm-google-shopping::custom-label-2']>div>ul>li:nth-child(1)>button")));
		first_filter_option_button.click();
		
		List<WebElement> filtered_items=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.boost-pfs-filter-product-item-image a")));
		Integer count_filtered_items=filtered_items.size();
		
		System.out.println(count_filtered_items);
		
		Assert.assertTrue(count_filtered_items.equals(first_filter_option_count_number));
		
		WebElement logo=driver.findElement(By.cssSelector("div#shopify-section-static-header-2 section div+div>div td a img"));
		logo.click();
		
	}
	
	@Test(priority=11)
	public void search_sku_validate_product_page() {
		
		WebElement item_Search=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#psHeaderSearchTextBox")));
		item_Search.sendKeys("0141093");
		
		WebElement search_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='form-field no-label']>button:nth-of-type(2)")));
		search_button.click();
		
		item_Search.sendKeys(Keys.ENTER);
		
		try {
			
			WebElement product_link=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='boost-pfs-filter-product-bottom'] a")));
			product_link.click();
			
		}catch(StaleElementReferenceException e) {
			
			driver.navigate().refresh();
			WebElement product_link=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='boost-pfs-filter-product-bottom'] a")));
			product_link.click();
			
		}
		
		//Verify product details page
		
		 //Verify that 3 thumbnails images are displayed
		List<WebElement> thumbnails=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("nav.product-gallery--navigation>button")));
		Integer thumnail_count=thumbnails.size();
		System.out.println("Number of thumbnails displayed " +thumnail_count);
		
		//hover on starts and click read review
		WebElement rating_button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class*='bv_main_container_row_flex bv_ratings_summary']")));
		a.moveToElement(rating_button).build().perform();
		
		WebElement read_review=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#bv_components_histogram>div+div")));
		read_review.click();
		
		//This code handles the shadow element to read the text rating snapshot
		WebElement shadowhost=driver.findElement(By.cssSelector("div#shopify-section-static-product>section>article+div"));
		SearchContext rootNode=shadowhost.getShadowRoot();
		String rating_snapshot=rootNode.findElement(By.cssSelector("#bv-reviews-rating-snapshot-container")).getText();
		System.out.println(rating_snapshot);
		
		
		
		
		
		
	}
	




}
