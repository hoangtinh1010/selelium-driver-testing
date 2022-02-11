package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_05_Element_Part_III {
	WebDriver driver;String projectPath = System.getProperty("user.dir");
	String firstName, lastName,fullName, emailAddress,password,nonEmailAddress;
	
	//Global variable
	By emailTextboxBy = By.id("email");
	By passwordTextboxBy = By.id("pass");
	By submitButtonBy = By.id("send2");
	By errorRequireEmailBy =  By.id("advice-required-entry-email");
	By errorRequirePasswordBy = By.id("advice-required-entry-pass");
	By errorValidEmailBy = By.id("advice-validate-email-email");
	By errorValidPasswordBy = By.id("advice-validate-password-pass");
	

	@BeforeClass //Chạy trước cho testcase đầu tiên
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		firstName = "Tinh";
		lastName = "Hoang";
		fullName = firstName +" " + lastName;
		emailAddress = "hoangtinh" + getRandomNumber() + "123@hotmail.net";
		password = "123456789";
		nonEmailAddress = "hoangtinh" + getRandomNumber() + "123@live.com";
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@BeforeMethod//Chạy trước cho tất cả testcase
	public void beforMethod() {
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	
	}
	
	@Test
	public void Login_01_Empty_Email_And_Password() {
		
		driver.findElement(emailTextboxBy).sendKeys("");
		driver.findElement(passwordTextboxBy).sendKeys("");
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(errorRequireEmailBy).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(errorRequirePasswordBy).getText(), "This is a required field.");
		
		
	}
	
	@Test
	public void Login_02_Invalid_Email() {
		
		driver.findElement(emailTextboxBy).sendKeys("123123@123.123");
		driver.findElement(passwordTextboxBy).sendKeys("123456");
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(errorValidEmailBy).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
		
	}
	
	@Test
	public void Login_03_Invalid_Password() {
		
		driver.findElement(emailTextboxBy).sendKeys("automation@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("123");
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(errorValidPasswordBy).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
		
	}
	
	@Test
	public void Login_04_Create_New_Account_Success( ) {
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		
		//verif
		Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(), "Hello, "+ fullName + "!");
		
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));
		
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		
		
	}
	
	@Test
	public void Login_05_Incorrect_Email_And_Password() {
		//Existed Email + incorrect Password -> Unsuccess
		driver.findElement(emailTextboxBy).sendKeys(emailAddress);
		driver.findElement(passwordTextboxBy).sendKeys("123456");
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[text() ='Invalid login or password.']")).getText(), "Invalid login or password.");
		
		//Non exited Email + Correct Password -> Unsuccess
		driver.findElement(emailTextboxBy).clear();
		driver.findElement(emailTextboxBy).sendKeys(nonEmailAddress);
		
		driver.findElement(passwordTextboxBy).clear();		
		driver.findElement(passwordTextboxBy).sendKeys("password");
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[text() ='Invalid login or password.']")).getText(), "Invalid login or password.");
	}
	
	@Test
	public void Login_06_Valid_Email_And_Password() {
		driver.findElement(emailTextboxBy).sendKeys(emailAddress);
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(submitButtonBy).click();	
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(), "Hello, "+ fullName + "!");
		
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));
		
	}


	@AfterClass
	public void afterClass() {
	driver.quit();
	} 
	
	@Test
	public int getRandomNumber() { 
		Random rand = new Random();
		return rand.nextInt(9999);
		
	}
}