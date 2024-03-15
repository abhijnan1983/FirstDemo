package maven_test.maven_project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class sampleTest {
	
	
	
	@Test
	public void smoke() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		driver.navigate().refresh();
		
		
		System.out.println("perform a smoke test after the feature change");
		System.out.println("Experiment");
	}

}
