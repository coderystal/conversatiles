package questiondecktesting;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConversatilesPOM {
	private WebDriver driver;
	private WebDriverWait wait;

	public ConversatilesPOM() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public WebElement getNumViewedButton() {
		return driver.findElement(new ById("numviewed"));
	}
	public WebElement getNumCardsButton() {
		return driver.findElement(new ById("numcards"));
	}
	public WebElement getResetButton() {
		return driver.findElement(new ById("resetbutton"));
	}
	public WebElement getBackButton() {
		return driver.findElement(new ById("backbtn"));
	}
	public WebElement getDrawButton() {
		return driver.findElement(new ById("drawbutton"));
	}
	public WebElement getCard() {
		return driver.findElement(new ById("question"));
	}
	public WebElement getModalContent() {
		return driver.findElement(new ByClassName("modal-content"));
	}
	
	public WebElement getEnteredCardNum() {
		return driver.findElement(new ById("enteredcardnum"));
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public WebDriverWait getWait() {
		return wait;
	}
}
