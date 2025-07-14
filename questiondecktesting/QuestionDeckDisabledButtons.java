package questiondecktesting;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QuestionDeckDisabledButtons {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		driver.get("https://htmlpreview.github.io/?https://github.com/coderystal/questiondeck/blob/main/v1/index.html");
		System.out.println("got");
		driver.manage().window().maximize();
		System.out.println("maximized");
		wait.until(ExpectedConditions.presenceOfElementLocated(new ById("question")));
		
		//check reset 10x *********************************************************************************RESET
		for (int resetNum = 0; resetNum < 10; resetNum++) {
			assertEquals("viewed enabled", true, driver.findElement(new ById("numviewed")).isEnabled());
			assertEquals("cards enabled", true, driver.findElement(new ById("numcards")).isEnabled());
			assertEquals("reset enabled", false, driver.findElement(new ById("resetbutton")).isEnabled());
			assertEquals("back enabled", false, driver.findElement(new ById("backbtn")).isEnabled());
			assertEquals("draw enabled", true, driver.findElement(new ById("drawbutton")).isEnabled());
			//flip random amount of cards
	    	int toFlip = resetNum == 0 ? 652 : (new Random()).nextInt(50);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				//click card or draw button
				driver.findElement(new ById((new Random()).nextInt(2) == 0 ? "question" : "drawbutton")).click();

				assertEquals("viewed enabled", true, driver.findElement(new ById("numviewed")).isEnabled());
				assertEquals("cards enabled", true, driver.findElement(new ById("numcards")).isEnabled());
				assertEquals("reset enabled", true, driver.findElement(new ById("resetbutton")).isEnabled());
				assertEquals("back enabled", flipped != 0, driver.findElement(new ById("backbtn")).isEnabled());
				assertEquals("draw enabled", flipped != 651, driver.findElement(new ById("drawbutton")).isEnabled());
			}

	    	System.out.println("reset");
	    	driver.findElement(new ById("resetbutton")).click();
		}
		
		driver.close();
    	System.out.println("done");
	}
}
