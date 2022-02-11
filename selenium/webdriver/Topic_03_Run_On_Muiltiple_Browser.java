package webdriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.testng.annotations.Test;

public class Topic_03_Run_On_Muiltiple_Browser {
   WebDriver driver;
   String projectPath = System.getProperty("user.dir");
   
   @Test
   public void TC_01_Firefox() {
	   //Executable File: chromedriver/geckodriver/edgedriver/IEDriver/..
	   System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
	   
	   //Class:FirefoxDriver/ChromeDriver/EdgeDriver/SafariDriver/..
	   driver = new FirefoxDriver();
	   
	   driver.get("https://vi-vn.facebook.com/");
	   
	   driver.quit();
   }
   @Test
   public void TC_02_Chrome() {
	   //Executable File: chromedriver/geckodriver/edgedriver/IEDriver/..
	   System.setProperty("webdriver.chrome.driver", projectPath + "\\BrowserDrivers\\chromedriver.exe");
	   
	   //Class:FirefoxDriver/ChromeDriver/EdgeDriver/SafariDriver/..
	   driver = new ChromeDriver();
	   
	   driver.get("https://vi-vn.facebook.com/");
	   
	   driver.quit();
   }
   @Test
   public void TC_03_Edge() {
	   //Executable File: chromedriver/geckodriver/edgedriver/IEDriver/..
	   System.setProperty("webdriver.edge.driver", projectPath + "\\BrowserDrivers\\msedgedriver.exe");
	   
	   //Class:FirefoxDriver/ChromeDriver/EdgeDriver/SafariDriver/..
	   driver = new EdgeDriver();
	   
	   driver.get("https://vi-vn.facebook.com/");
	   
	   driver.quit();
   }
}
