package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Action_Part_1 {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//1-Khởi tạo action
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	
	}

	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		WebElement yourAgeTextbox = driver.findElement(By.id("age"));
		
		//Hower chuột vào textbox
		action.moveToElement(yourAgeTextbox).perform();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(),"We ask for your age only for statistical purposes.");
		
		
	}

	
	public void TC_02_Hover() {
		driver.get("http://www.myntra.com/");
		
		//2-Gọi hàm cần dùng
		//3-Gọi hàm perform()
		action.moveToElement(driver.findElement(By.xpath("//header//a[text()='Kids']"))).perform();
		sleepInSecond(3);
		
		action.click(driver.findElement(By.xpath("//header//a[text()='Home & Bath']"))).perform();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
		
	}

	@Test
	public void TC_03_Click_And_Hold() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
		//Khai báo tất cả 12 elements
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol.ui-selectable>li"));
		
		//1 ->4
		//click and hold vào 1
		//hover tới 4
		//Nhả chuột trái ra
		//Thực thi các câu lệnh
		action.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(3)).release().perform();		
		
		List<WebElement> allNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelected.size(), 4);
	}
		

	@Test
	public void TC_03_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol.ui-selectable>li"));
		//1 và 5 và 11
		//Nhấn phím ctrl
		//Click vào 5 và click vào 11
		//Thực thi các câu lệnh
		//Nhả ctrl
		action.keyDown(Keys.CONTROL).perform();
		action.click(allNumbers.get(0)).click(allNumbers.get(4)).click(allNumbers.get(10)).perform();
		action.keyUp(Keys.CONTROL).perform();
		sleepInSecond(5);
		
		List<WebElement> allNumberSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelected.size(), 3);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}