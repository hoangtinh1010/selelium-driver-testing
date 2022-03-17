package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Frame_Iframe {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	
	public void TC_01_Kyna() {
		//A
		driver.get("https://kyna.vn/");
		
		//Switch vào frame/iframe trước rồi mới thao tác lên element thuộc frame/iframe đó
		//cách 1 "driver.switchTo().frame(0);
		//cách 2 switch to element
		//A->B
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.face-content>iframe")));
		//verify số lượng like trên fanpage là 167
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "167K likes") ;
		
		//B->A
		driver.switchTo().defaultContent();
		//Click vào chat để bật popup lên
		//A->C
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("Hoang Tinh");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0122222222");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Iframe");
		
		//C->A
		driver.switchTo().defaultContent();
		
		String keyword = "Excel";
		//A
		//Nhập và search
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		
		//Verify course name chứa từ khóa vừa nhập
		List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));
		for (WebElement course : courseName) {
			System.out.println(course.getText());
			Assert.assertTrue(course.getText().contains(keyword));
		}
		
	
	}


	public void TC_02_Blog() {
		//A (automation.com)
		driver.get("https://automationfc.com/2020/02/18/training-online-automation-testing/");
		
		//A -> B (Youtube)
		driver.switchTo().frame("video-2679-1_youtube_iframe");
		
		//B
		driver.findElement(By.cssSelector("button.ytp-large-play-button")).click();
		sleepInSecond(5);
		
		//B->A 
		driver.switchTo().defaultContent();
		
		//A -> C (Facebook)
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fb-page iframe")));
		
		//C
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Automation FC']/parent::div/following-sibling::div")).getText(), "3,206 likes");
			
		
	}


	@Test
	public void TC_03_HDFC() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		//A->B
		driver.switchTo().frame("login_page");
		
		//B
		driver.findElement(By.name("fldLoginUserId")).sendKeys("fldLoginUserId");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		//B-Password textbox is display
		Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
		
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