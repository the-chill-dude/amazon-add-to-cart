package amazontest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AmazonTestNG {
	
	public String baseURL = "https://www.amazon.com/";
	public WebDriver driver;
	public String driverPath = "./driver/chromedriver.exe";
	
	@BeforeTest
	public void browserLaunch()  {
		System.out.println("Launching the Browser..!!");		
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(baseURL);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@Test(priority = 0)
	public void menuNavigation() {
		driver.findElement(By.xpath("//*[@class='hm-icon nav-sprite']")).click();
		List<WebElement> shopByCategory = driver.findElements(By.xpath("//ul[@class='hmenu hmenu-visible']/child::li/a/div"));
		
		
		for(WebElement sCategory : shopByCategory){
			if(sCategory.getText().equalsIgnoreCase("Electronics")) {
				sCategory.click();
			}
			
		}
	}
	
	@Test(priority = 1)
	public void subMenuNavigation() {
		List<WebElement> subCategoryMenu = driver.findElements(By.xpath("//ul[@class='hmenu hmenu-visible hmenu-translateX']/child::li/a[@class='hmenu-item']"));
		
		for(WebElement sMenu : subCategoryMenu){
			if(sMenu.getText().equalsIgnoreCase("Television & Video")){
				sMenu.click();
			}
			
		}
	}
	
	@Test(priority = 2)
	public void searchInTelevisionVideo() {
				
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='Go']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", submitButton);
		
	}
	
	@Test(priority = 3)
	public void selectingTVSize() {
		List<WebElement> televisionSize = driver.findElements(By.xpath("//h3[contains(text(),'Televisions by Size')]/following-sibling::ul/li/a[contains(text(),'Inches')]"));
		
		for(WebElement sizeTV : televisionSize){
			if(sizeTV.getText().equalsIgnoreCase("32 Inches and Under")){
				sizeTV.click();
			}
			
		}
	}
	
	@Test(priority = 4)
	public void sortingByPrice() {
		
		driver.findElement(By.xpath("//span[@class='a-dropdown-prompt']")).click();
		
		List<WebElement> sortList = driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));
		
		for(WebElement sort : sortList){
			if(sort.getText().equalsIgnoreCase("Price: Low to High")){
				sort.click();
			}
			
		}
	}
	
	@Test(priority = 5)
	public void listPrint() {
		
		String[] listOfItems = selectingItemList();
		for(String str : listOfItems) {
			System.out.println(str);
		}
		
		String[] listOfProducts = productByYear();
		for(String str1 : listOfProducts) {
			System.out.println(str1);
		}
		
		
	}
		
	@Test
	public String[] selectingItemList() {
		
		/**
		 * As the Price of items are not available, hence only Items name
		 */
		
		List<WebElement> itemNameList = driver.findElements(By.xpath("//*[@class='s-main-slot s-result-list s-search-results sg-row']//a[@class='a-link-normal a-text-normal']"));
		
		String[] itemsName =new String[itemNameList.size()];
		int i=0;
		
		for(WebElement itemsList : itemNameList){
			itemsName[i++] = itemsList.getText();
			}
		return itemsName;
	}
	
	@Test
	public String[] productByYear() {
		
		/**
		 * As the Price of items are not available, hence only by Year
		 */
		List<WebElement> productsList = driver.findElements(By.xpath("//*[@class='s-main-slot s-result-list s-search-results sg-row']//a[@class='a-link-normal a-text-normal']"));
		
		String[] productsByYear =new String[productsList.size()];
		int i=0;
		
		for(WebElement products : productsList){
			if(products.getText().contains("2017")){
				productsByYear[i++] = products.getText();
			}
			
			}
		return productsByYear;
	}
	
	@Test(priority = 6)
	public void addProduct() {
		driver.findElement(By.xpath("//*[@data-image-index='0']")).click();
		driver.findElement(By.xpath("//a[@id='wishListMainButton-announce']")).click();
		
		//driver.findElement(By.xpath("//*[contains(text(),'Proceed to checkout')][@id='hlb-ptc-btn-native']")).click();
		
	}
	
	@Test(priority = 7)
	public void signInPage() {
		String actualSignIn = driver.getTitle();
		
		String expectedSignIn = "Amazon Sign-In";
		
		Assert.assertEquals(actualSignIn, expectedSignIn);
	}
	
	@Test(priority = 8)
	public void login() {
		driver.findElement(By.xpath("//*[@id='ap_email']")).sendKeys("user@amazon.com");
		driver.findElement(By.xpath("//*[@id='continue'][@type='submit']")).submit();
		
		driver.findElement(By.xpath("//*[@id='ap_password']")).sendKeys("password");
		driver.findElement(By.xpath("//*[@id='signInSubmit']")).click();
	}
	
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

}
