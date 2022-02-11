package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	
	String projectPath = System.getProperty("user.dir");


	
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//wait cho các trạng thái của element: visible/presence/invisible/staleness
		explicitWait = new WebDriverWait(driver,15);
		
		//Ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;
		
		//Wait cho việc tìm element (findElement)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_Jquery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdownList ("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "5"); 
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "5");
		
		selectItemInCustomDropdownList ("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "15"); 
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "15");
		
		selectItemInCustomDropdownList ("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "19");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "19");
		
		selectItemInCustomDropdownList ("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "3");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "3");
		

		//span#number-button>span.ui-selectmenu-icon
		//ul#number-menu div
		//5
		
	}
	public void selectItemInCustomDropdownList(String parentLocator,  String childLocator,  String expectedTextItem) {
	
		// Step 1: click vào 1 element cho nó xổ hết ra các item
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(2);
		
		// - Step 2: Chờ cho các item load hết ra thành công 
		//Lưu ý 1: Locator chứa hết tất cả các item
		//Lưu ý 2: Locator phải đến note cuối cùng chywas text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));
		
		//- Step 3: Tìm item cần chọn

		
		//Lấy tất cả các element (item) ra sau đó duyệt qua từng item
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		
		//Duyệt qua từng item getText của item ra
		for (WebElement item : allItems) {
			String actualText = item.getText();
			System.out.println("Actual Text = " +actualText);
			
			//Nếu text = text mình mong muốn (item cần click vào)
			if (actualText.equals(expectedTextItem)) {
				// +B1: Nếu item cần chọn nằm trong vùng nhìn thấy thì ko cần scroll xuống tới element tìm tiếp
				// +B2: Nếu item cần hconj nằm  ở dưới thì scroll xuống đến item đó
				
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				
				//Step 4: Click vào item đó
				item.click();
				
				//Thoát khỏi vòng lặp không có kiểm tra element tiếp theo nữa
				break;
				
			}
		}
		
		sleepInSecond(10);
		
	}

	@Test
	public void TC_02_React() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInCustomDropdownList ("i.dropdown", "div.item>span.text", "Stevie Feliciano"); 
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
		
		selectItemInCustomDropdownList ("i.dropdown", "div.item>span.text", "Matt"); 
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Matt");
		
		selectItemInCustomDropdownList ("i.dropdown", "div.item>span.text", "Justen Kitsune"); 
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Justen Kitsune");
		
	}

	@Test
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
}