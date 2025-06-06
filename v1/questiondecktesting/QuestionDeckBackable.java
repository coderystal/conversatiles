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

public class QuestionDeckBackable {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		driver.get("https://htmlpreview.github.io/?https://github.com/coderystal/questiondeck/blob/main/v1/index.html");
		System.out.println("got");
		driver.manage().window().maximize();
		System.out.println("maximized");
		wait.until(ExpectedConditions.presenceOfElementLocated(new ById("question")));		

		//check back 10x *********************************************************************************BACK
		int totFlipped = 0;
		int backable = 0;
		for (int backRounds = 0; backRounds < 10; backRounds++) {			
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(50);
	    	System.out.println("flipping " + toFlip);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				//click card or draw button
				driver.findElement(new ById((new Random()).nextInt(2) == 0 ? "question" : "drawbutton")).click();
				totFlipped++;
				backable = totFlipped == 1 ? 0 : backable+1;
				assertEquals("back enabled", backable != 0, driver.findElement(new ById("backbtn")).isEnabled());
			}
			

	    	int toBack = (new Random()).nextInt(totFlipped + 1);
	    	System.out.println("backing " + toBack);
			for (int backed = 0; backed < toBack; backed++) {
				//click back
				driver.findElement(new ById("backbtn")).click();
				backable--;
				assertEquals("back enabled", backable != 0, driver.findElement(new ById("backbtn")).isEnabled());
				if (backable == 0) {
			    	System.out.println("actually backed " + (backed+1));
					break;
				}
			}
		}

    	System.out.println("backing remaining " + backable);
		for (int backed = 0; backed < backable; backed++) {
			//click back
			driver.findElement(new ById("backbtn")).click();
		}
		assertEquals("back enabled", false, driver.findElement(new ById("backbtn")).isEnabled());
		
		driver.close();
    	System.out.println("done");
	}
}
