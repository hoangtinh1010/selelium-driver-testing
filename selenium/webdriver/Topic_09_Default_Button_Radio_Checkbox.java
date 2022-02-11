package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Default_Button_Radio_Checkbox {
	WebDriver driver;String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor; 
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		jsExecutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create?attempt=1");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		
		By loginButton = By.cssSelector("button.fhs-btn-login");
		
		//verify login button is disabled
	Assert.assertFalse(driver.findElement(loginButton).isEnabled());
	
	driver.findElement(By.cssSelector("input#login_username")).sendKeys("automationfc@gmail.com");
	driver.findElement(By.cssSelector("input#login_password")).sendKeys("automationfc");
	
	//Verify login button is enabled
	Assert.assertTrue(driver.findElement(loginButton).isEnabled());
	
	//verify background color = RED
	String loginButtonBackgroundColorRbg = driver.findElement(loginButton).getCssValue("background-color");
	System.out.println("RGB color = " + loginButtonBackgroundColorRbg);
	
	//Verify = RGB
	Assert.assertEquals(loginButtonBackgroundColorRbg,"rgb(201, 33, 39)");
	
	//convert qua Hexa
	String loginButtonBackgroundColorHexa = Color.fromString(loginButtonBackgroundColorRbg).asHex();
	System.out.println("Hexa color = " + loginButtonBackgroundColorRbg);
	Assert.assertEquals(loginButtonBackgroundColorHexa.toUpperCase(), "#C92127");
	
	driver.navigate().refresh();
	
	driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
	
	//Remove disabled attribute
	jsExecutor.executeScript("arguments[0].removeAttribute('disabled');",driver.findElement(loginButton));
	
	sleepInSecond(2);
	
	driver.findElement(loginButton).click();
	
	Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
	Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']']")).getText(), "Thông tin này không thể để trống");
		
	}

	//@Test
	public void TC_02_Default_Radio() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		By oneDotEightPetrolRadio = By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input");
		By twoDotZedoPetrolRadio = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
		By threeDotSixPetrolRadio = By.xpath("//label[text()='3.6 Petrol, 191kW']/preceding-sibling::input");
		
		Assert.assertFalse(driver.findElement(oneDotEightPetrolRadio).isSelected());
		
		driver.findElement(oneDotEightPetrolRadio).click();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(oneDotEightPetrolRadio).isSelected());
		
		driver.findElement(twoDotZedoPetrolRadio).click();
		sleepInSecond(2);
		
		//Deselected
		Assert.assertFalse(driver.findElement(oneDotEightPetrolRadio).isSelected());
		
		//Selected
		Assert.assertTrue(driver.findElement(twoDotZedoPetrolRadio).isSelected());
		
		//Enabled
		Assert.assertTrue(driver.findElement(oneDotEightPetrolRadio).isEnabled());
		Assert.assertTrue(driver.findElement(oneDotEightPetrolRadio).isEnabled());
		
		//Disabled = Read only
		Assert.assertFalse(driver.findElement(threeDotSixPetrolRadio).isEnabled());
		
	}

	//@Test
	public void TC_03_Defaul_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		By luggageCheckbox = By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		By heatedCheckbox = By.xpath("//label[text()='Heated front and rear seats']/preceding-sibling::input");
		By towbarCheckbox = By.xpath("//label[text()='Towbar preparation']/preceding-sibling::input");
		By leatherCheckbox = By.xpath("//label[text()='Leather trim']/preceding-sibling::input");
		
		//select
		checkToCheckBox(luggageCheckbox);
		checkToCheckBox(heatedCheckbox);
				
		//Selected
		Assert.assertTrue(isElementSelected(luggageCheckbox));
		Assert.assertTrue(isElementSelected(heatedCheckbox));
		Assert.assertTrue(isElementSelected(leatherCheckbox));
		
		//Disabled
		Assert.assertFalse(driver.findElement(leatherCheckbox).isEnabled());
		Assert.assertFalse(driver.findElement(towbarCheckbox).isEnabled());
		
		//De-select
		uncheckToCheckBox(luggageCheckbox);
		uncheckToCheckBox(heatedCheckbox);
	
		//De-selected
		Assert.assertFalse(driver.findElement(towbarCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(luggageCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(heatedCheckbox).isSelected());	
		
	}
	
	@Test
	public void TC_04_Multiple_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		List<WebElement> checkboxes =  driver.findElements(By.cssSelector("input[type='checkbox']"));
		System.out.println("Checkbox size =" + checkboxes.size());
		
		//Action - Select
		for (WebElement checkbox : checkboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();
				sleepInMiliSecond(500);
			}
		}
		
		//Verify - selected
		for (WebElement checkbox : checkboxes) {
			Assert.assertTrue(checkbox.isSelected());			
		}
		
		//Action - Deselect
		for (WebElement checkbox : checkboxes) {
			if (checkbox.isSelected()) {
				checkbox.click();
				sleepInMiliSecond(500);
			}
		}
		
		//Verify - deselected
		for (WebElement checkbox : checkboxes) {
			Assert.assertFalse(checkbox.isSelected());
		}
		
	}
	
	public void checkToCheckBox(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
		
	}
	
	public void uncheckToCheckBox(By by) {
		if(driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}
	public boolean isElementSelected(By by) {
		if (driver.findElement(by).isSelected()) {
			return true;
		} else {
			return false;
		}
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
	
	public void sleepInMiliSecond(long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}