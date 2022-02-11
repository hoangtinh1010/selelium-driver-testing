package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_04_Browser_Element_Method {
	WebDriver driver;String projectPath = System.getProperty("user.dir");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}

	@Test
	public void TC_01_Browser() {
		//Các hàm/method để thao tác với Browser là thông qua biến driver
		
		//Mở ra Url
		driver.get("https://www.sendo.vn/");
		driver.get("https://tiki.vn/");
		
		//Đóng browser nếu chỉ có 1 tab
		//Đóng browser nếu có nhiều tab
		driver.close();
		
		//Đóng browserkhoong quan tâm bao nhiêu tab
		driver.quit();
		
		//Tìm 1 element trên page
		//Trả về dât type là WebElement
		//Không nên lưu thành 1 biến  - Tương tác sử dụng 1 lần,Nên lưu thành 1 biến  - Tương tác sử dụng lại nhiều lần
		WebElement emailTextbox = driver.findElement(By.id("email"));
	
		
		//Tìm nhiều hơn 1 element trên page
		//Trả về data type là List<WebElement>
		List<WebElement>link = driver.findElements(By.xpath("//a"));
		
		//Trả về Url của page hiện tại
		String homePageUrl = driver.getCurrentUrl();
		System.out.println(homePageUrl);
		
		//Verify dữ liệu này đúng như mong đợi
		Assert.assertEquals(homePageUrl, "https://tiki.vn/");
		
		//Không nên lưu thành 1 biến - Tương tác trực tiếp luôn
	}

	@Test
	public void TC_02_Element() {
		
	}

	@Test
	public void TC_03_d() {
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}