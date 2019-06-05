package tqs.api;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionalTest {
  

  @Test
  public void testUntitledTestCase() throws Exception {
	  WebDriver driver = new ChromeDriver();
	  
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  String url = "file:///C:/Users/Asus/Desktop/tqs/tqs.api/target/classes/HTML/index.html";
	    driver.get(url);
	    assertEquals("Aveiro", findElement(driver, By.id("locationToday"), 1000));
	    new Select (driver.findElement(By.id("list"))).selectByVisibleText("Lisbon");
	    assertEquals("Lisbon", findElement(driver, By.id("locationToday"), 1000));
	    driver.findElement(By.id("modal")).click();
	    driver.findElement(By.id("cidadeInput")).sendKeys("electricmoon");
	    driver.findElement(By.id("latitude")).sendKeys("40");
	    driver.findElement(By.id("longitude")).sendKeys("-112");
	    driver.findElement(By.id("adicionarCidade")).click();
	    Thread.sleep(1000);
	    new Select (driver.findElement(By.id("list"))).selectByVisibleText("California");
	    assertEquals("California", findElement(driver, By.id("locationToday"), 1000));
	    driver.quit();
  }
  
  public static WebElement findElement(WebDriver driver, By selector, long timeOutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
	    wait.until(ExpectedConditions.presenceOfElementLocated(selector));
	    return driver.findElement(selector);
	}
}
