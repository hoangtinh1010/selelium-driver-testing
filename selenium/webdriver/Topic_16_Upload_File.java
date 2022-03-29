package webdriver;

import static org.testng.Assert.assertTrue;

import java.io.File;
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

public class Topic_16_Upload_File {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	//D:\\TinhHT\\Fullstack Selenium Java\\02-Selenium WebDriver
	String iphoneFileName = "iphone.jpg";
	String macbookFileName = "macbook.jpg";
	String samsungFileName = "samsung.jpg";
	
	//Support cho các máy Window
	String uploadFileFolderPath = projectPath + File.separator + "uploadFiles" + File.separator;
	String iphoneFilePath = uploadFileFolderPath + iphoneFileName;
	String macbookFilePath = uploadFileFolderPath + macbookFileName;
	String samsungFilePath = uploadFileFolderPath + samsungFileName;
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
   
	public void TC_01_One_File_One_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFileBy = By.xpath("//input[@type='file']");
		
		//Load file
		driver.findElement(uploadFileBy).sendKeys(iphoneFilePath);
		driver.findElement(uploadFileBy).sendKeys(macbookFilePath);
		driver.findElement(uploadFileBy).sendKeys(samsungFilePath);
		sleepInSecond(3);
		
		//Verify file loaded success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + iphoneFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + macbookFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + samsungFileName +"']")).isDisplayed());
		
		//Click to Upload button at each file 
		List<WebElement> uploadButtons = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : uploadButtons) {
			button.click();
			sleepInSecond(2);
		}
		
		//Verify file uploaded success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + iphoneFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + macbookFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + samsungFileName +"']")).isDisplayed());		
	}


	public void TC_02_Multilple_File_One_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFileBy = By.xpath("//input[@type='file']");
		
		//Load 3 files/time
		driver.findElement(uploadFileBy).sendKeys(iphoneFilePath + "\n" + macbookFilePath + "\n" + samsungFilePath);
		sleepInSecond(3);
		
		//Verify file loaded success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + iphoneFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + macbookFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + samsungFileName +"']")).isDisplayed());
		
		//Click to Upload button at each file 
		List<WebElement> uploadButtons = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : uploadButtons) {
			button.click();
			sleepInSecond(2);
		}
		
		//Verify file uploaded success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + iphoneFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + macbookFileName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@title='" + samsungFileName +"']")).isDisplayed());	
	}

	@Test
	public void TC_03_Go_File() {
		driver.get("https://gofile.io/uploadFiles");
		By uploadFileBy = By.xpath("//input[@type='file']");
		
		//Load 3 files/time
		driver.findElement(uploadFileBy).sendKeys(iphoneFilePath + "\n" + macbookFilePath + "\n" + samsungFilePath);
		sleepInSecond(10);
		
		Assert.assertTrue(driver.findElement(By.xpath("//h5[text()='Your files have been successfully uploaded']")).isDisplayed());
		 
		String uploadLink = driver.findElement(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")).getAttribute("href");
		
		driver.get(uploadLink);
		
		//Download button
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + iphoneFileName + "']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + macbookFileName + "']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + samsungFileName + "']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")).isDisplayed());
		
		//Play button
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + iphoneFileName + "']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + macbookFileName + "']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='"+ samsungFileName + "']/parent::a/parent::div/following-sibling::div//button[contains(@class,'contentPlay')]")).isDisplayed());
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