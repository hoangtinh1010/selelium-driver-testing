package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Default_Dropdown {
	WebDriver driver;String projectPath = System.getProperty("user.dir");
	
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Actions action;
	Select select;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//Khởi tạo sau khi driver này được sinh ra
		//JavascriptExecutor/WebDriverWait/Action,...
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 30);
		action = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	//Test
	public void TC_01_Rode() {
		driver.get("https://rode.com/wheretobuy");
		//Khởi tạo khi sử dụng
		//Khởi tạo select để thao tác với element (country dropdown)
		select = new Select(driver.findElement(By.xpath("//select[@id='where_country']")));
		
		//Không support multiple select
		Assert.assertFalse(select.isMultiple());
		
		//Select giá trị Việt Nam
		select.selectByVisibleText("Vietnam");
		sleepInSecond(5);
		
		//Kiểm tra giá trị đc chọn thành cong
		Assert.assertEquals(select.getFirstSelectedOption().getText(),"Vietnam");
		
		//click button search, kiểm tra có 32 giá trị trả về
		driver.findElement(By.xpath("input#'search_loc_submit")).click();
		sleepInSecond(5);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result_count>span")).getText(), "32");
		
		List<WebElement> storeName = driver.findElements(By.cssSelector("div#search_results div.store_name"));
		
		//Verify lại số lượng 32
		Assert.assertEquals(storeName.size(), 32);
		
		for (WebElement store : storeName) {
			System.out.println(store.getText());
			
		}
		
	}

	@Test
	public void TC_02_NopCommerce() {
		String firstName = "Hoang";
		String lastName = "Tinh";
		String date = "10";
		String month = "October";
		String year = "2000";
		String emailAddress = "hoangtinh" + getRandomNumber() + "123@hotmail.net";
		String password = "123456";
		
		driver.get("https://demo.nopcommerce.com/register");
		
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		
		//Date
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(date);
		
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
		
		driver.findElement(By.cssSelector("a.ico-account")).click();
		
		//Page HTML Render lại
		//verify
		Assert.assertEquals(driver.findElement(By.name("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("LastName")).getAttribute("value"), lastName);
		
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
	
		Assert.assertEquals(driver.findElement(By.name("Email")).getAttribute("value"), emailAddress);
	}

	//Test
	public void TC_03_d() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public int getRandomNumber() { 
		Random rand = new Random();
		return rand.nextInt(9999);
		
	}
}